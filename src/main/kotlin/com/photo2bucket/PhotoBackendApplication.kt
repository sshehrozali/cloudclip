package com.photo2bucket

import com.google.cloud.spring.data.datastore.core.mapping.Entity
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository
import com.google.cloud.spring.vision.CloudVisionTemplate
import com.google.cloud.vision.v1.Feature
import org.apache.catalina.core.ApplicationContext
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.core.io.WritableResource
import org.springframework.data.annotation.Id
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

@SpringBootApplication
class PhotoBackendApplication

fun main(args: Array<String>) {
	runApplication<PhotoBackendApplication>(*args)
}
@Entity
data class Photo(
	@Id
	var id: String? = null,
	var uri: String? = null,
	var label: String? = null
)

@RepositoryRestResource
interface PhotoRepository : DatastoreRepository<Photo, String>

@RestController
class HelloController (
	private val photoRepository: PhotoRepository
		) {
	@GetMapping("/")
	fun hello() = "Hello! I am from Spring Boot Kotlin!"

	@PostMapping("/photo")
	fun create(@RequestBody photo: Photo) {
		photoRepository.save(photo)	// Save JSON payload photo object
	}
}
@RestController
@RequestMapping("/api/")
class PhotoController (
	private val photoRepository: PhotoRepository,
	private val ctx: org.springframework.context.ApplicationContext,
	private val visionTemplate: CloudVisionTemplate
		) {

	private val bucket = "gs://magical-photos-bucket"
	// Method to upload photos to Cloud Bucket
	@PostMapping("upload")
	fun upload(@RequestParam("file") file: MultipartFile) : Photo {
		val id = UUID.randomUUID().toString()	// Randomly generate Filename for photo
		val uri = "$bucket/$id"		// URI to access photo from bucket
		val gcs = ctx.getResource(uri) as WritableResource		// Write as writeable resource

		print("$id $uri")

		// Upload file to Cloud Bucket
		file.inputStream.use {
			input -> gcs.outputStream.use { output ->
			input.copyTo(output)
		}
		}

		// Analyze image using Cloud Vision API
		val response = visionTemplate.analyzeImage(file.resource, Feature.Type.LABEL_DETECTION)
		System.out.println(response)
		// Get image labels
		val labels = response.labelAnnotationsList.take(5).map { it.description }.joinToString { "," }

		// Return JSON payload after saving on Cloud Firestore
		return photoRepository.save(Photo(id = id, uri = uri, label = labels))
	}

	// Method to retrieve URI of photo
	@GetMapping("/image/{id}")
	fun get(@PathVariable id: String) : ResponseEntity<Resource> {
		val resource = ctx.getResource("/$bucket/$id")
		return if (resource.exists()) {
			ResponseEntity.ok(resource)
		} else {
			ResponseEntity(HttpStatus.NOT_FOUND)
		}
	}
}