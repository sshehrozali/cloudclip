**Technologies used:**
* Spring Boot
* Kotlin
* Google Cloud
* Maven

**AppEngine deployment:**  
**app.yaml** - Specified instance properties and instance class type F4 supporting automatic scaling. Used Java 17 runtime environment.

**Google AI/ML:**  
**Cloud Vision API** - for analyzing images and extracting image annotations labels and saving to Cloud Firestore collection by JSON payload

**GET Request:**  
`{  
    id: image_filename_saved_on_bucket,  
    uri: bucket_uri,  
    label: image_annotations_analyzed_by_visionAPI  
}
`

**POST Request:**  
_Content-Type: multipart/form-data; boundary=WebAppBoundary_  
_--WebAppBoundary_
_Content-Disposition: form-data; name="file"; filename="img.jpg"_

**Continuous Deployment Pipeline:**
* Cloud Source Repositories (Source repository)  
* Cloud Build (for building .jar file using inline .yaml)  
* Container Registry (for building container image DockerFile)  
* CLoud Run (running service at endpoint)