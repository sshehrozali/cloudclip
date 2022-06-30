package com.example.photobackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class PhotoBackendApplication

fun main(args: Array<String>) {
	runApplication<PhotoBackendApplication>(*args)
}

@RestController
class HelloController {
	@GetMapping("/")
	fun hello() = "Hello! I am from Spring Boot Kotlin!"
}