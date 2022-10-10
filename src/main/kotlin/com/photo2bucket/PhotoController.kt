package com.photo2bucket

import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1")
class PhotoController(
    private val photoService: PhotoService
) {
    @GetMapping("/image/{id}")
    fun retrieveAPhoto(@PathVariable id: String): ResponseEntity<Resource> {
        return photoService.retrievePhoto(id)
    }

    @PostMapping("/upload")
    fun upload(@RequestParam("file") file: MultipartFile): Photo {
        return photoService.uploadPhoto(file)
    }
}