package com.example.netlib.domain.model.Auth

import kotlinx.serialization.Serializable


data class ErrorResponse(
    val status: Int,
    val message: String,
    val data: Map<String, Any> = emptyMap()
)
