package com.photo2bucket

import com.google.cloud.spring.data.datastore.core.mapping.Entity
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id

@Entity
@AllArgsConstructor
@NoArgsConstructor
class Photo(id: String, uri: String, label: String) {
    @Id
    lateinit var id: String
    lateinit var uri: String
    lateinit var label: String
}