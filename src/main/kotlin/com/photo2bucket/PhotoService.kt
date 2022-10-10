package com.photo2bucket

import com.google.cloud.spring.vision.CloudVisionTemplate
import org.springframework.core.io.Resource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class PhotoService (
    private val photoRepository: PhotoRepository,
    private val ctx: org.springframework.context.ApplicationContext,
    private val visionTemplate: CloudVisionTemplate
        ) {
    // Bucket name
    private val bucket = "gs://magical-photos-bucket"

    // Method to retrieve Photo from Storage Bucket
    fun retrievePhoto(id: String) : ResponseEntity<Resource> {
        val resource = ctx.getResource("/$bucket/$id")
        return if (resource.exists()) {
            ResponseEntity.ok(resource)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}