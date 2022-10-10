package com.photo2bucket

import com.google.cloud.spring.vision.CloudVisionTemplate
import com.google.cloud.vision.v1.Feature
import org.springframework.core.io.Resource
import org.springframework.core.io.WritableResource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/api/v1")
class PhotoController (
    private val photoRepository: PhotoRepository,
    private val ctx: org.springframework.context.ApplicationContext,
    private val visionTemplate: CloudVisionTemplate
        ) {
    // Bucket name
    private val bucket = "gs://magical-photos-bucket"

    // Method to retrieve a Photo
    @GetMapping("/image/{id}")
    fun get(@PathVariable id: String) : ResponseEntity<Resource> {
        val resource = ctx.getResource("/$bucket/$id")
        return if (resource.exists()) {
            ResponseEntity.ok(resource)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Method to upload new Photo
    @PostMapping("/upload")
    fun upload(@RequestParam("file") file: MultipartFile) : Photo {
        val id = UUID.randomUUID().toString()	// Randomly generate Filename for photo
        val uri = "$bucket/$id"		// URI to access photo from bucket
        val gcs = ctx.getResource(uri) as WritableResource        // Write as writeable resource

        // Upload file to Cloud Bucket
        file.inputStream.use {
                input -> gcs.outputStream.use { output ->
            input.copyTo(output)
        }
        }
        // Analyze image using Cloud Vision API
        val response = visionTemplate.analyzeImage(file.resource, Feature.Type.LABEL_DETECTION)
        // Get image labels
        val labels = response.labelAnnotationsList.take(5).map { it.description }.joinToString { "," }

        // Save Photo in DB
        return photoRepository.save(Photo(id = id, uri = uri, label = labels))
    }
}