package com.example.netlib.domain.model.Auth

import kotlinx.serialization.Serializable

@Serializable

data class ErrorResponse(
    val status: Int,
    val message: String,
    val data: String
)
