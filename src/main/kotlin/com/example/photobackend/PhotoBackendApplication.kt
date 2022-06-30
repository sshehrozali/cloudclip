package com.example.photobackend

import com.google.cloud.spring.data.datastore.core.mapping.Entity
import com.google.cloud.spring.data.datastore.repository.DatastoreRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

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

@Repository
interface PhotoRepository : DatastoreRepository<Photo, String>

@RestController
class HelloController (
	private val photoRepository: PhotoRepository
		) {
	@GetMapping("/")
	fun hello() = "Hello! I am from Spring Boot Kotlin!"

	@PostMapping("/photo")
	fun create(@RequestBody photo: Photo) {
		photoRepository.save(photo)		// Save JSON payload photo object
	}
}