package com.example.netlib.domain.model.Auth


data class AuthWithPasswordRequest(
    val identity: String,
    val password: String,
    val identityField: String
)