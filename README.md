### What's this?
Photo-2-Bucket is an application that is designed to upload Photos/Images/Pictures instantly to **Google Cloud Storage Bucket** which is a Google Cloud service for **Object Storage** or as **BLOB** (Binary Large Object) such as Files/CSVs/Documents etc. use to upload/retrieve for transaction purposes or for your any specific needs.

#### How it works?
The application uses RESTful architecture to upload or retrieve Photo from Cloud.
* **Uploading a Photo:** Make a _POST_ request to `HOST:8080/api/upload` with Image/Photo as _File_ attached with _Content-Type: multipart/form; boundary=WebAppBoundary_ and _Content-Disposition_ (check out _RESTClient.http_ to understand more about the request type). It will upload or save your Photo to Storage Bucket.
* **Retrieving a Photo:** Make a _GET_ request to `HOST:8080/api/image/{id}` to retrieve a specific photo from Storage Bucket. The `id` passed in URI request parameter is the id of the photo you want to retrieve from Storage Bucket. The response returned is the Photo (retrieved by id) with a status code of 200 OK.

