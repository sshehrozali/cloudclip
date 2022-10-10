package com.photo2bucket

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PhotoBackendApplication

fun main(args: Array<String>) {
    runApplication<PhotoBackendApplication>(*args)
}