package com.example.netlib.domain.model

data class UploadFile(
    val bytes: ByteArray,
    val name: String,
    val mimeType: String
)
