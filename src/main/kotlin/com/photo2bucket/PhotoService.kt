package com.photo2bucket

import com.google.cloud.spring.vision.CloudVisionTemplate
import org.springframework.stereotype.Service

@Service
class PhotoService (
    private val photoRepository: PhotoRepository,
    private val ctx: org.springframework.context.ApplicationContext,
    private val visionTemplate: CloudVisionTemplate
        ) {
    // Bucket name
    private val bucket = "gs://magical-photos-bucket"

}