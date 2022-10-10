package com.photo2bucket

import com.google.cloud.spring.data.datastore.core.mapping.Entity
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id

@Entity
@AllArgsConstructor
@NoArgsConstructor
class Photo {
    @Id
    var id: String? = null
    var uri: String? = null
    var label: String? = null
}