package com.example.netlib.domain.model.Auth

data class ErrorResponse(
    val status: Int,
    val message: String,
    val data: String
)
