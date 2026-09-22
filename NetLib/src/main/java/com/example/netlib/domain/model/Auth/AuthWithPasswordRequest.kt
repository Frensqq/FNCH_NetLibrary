package com.example.netlib.domain.model.Auth

import kotlinx.serialization.Serializable

@Serializable

data class AuthWithPasswordRequest(
    val identity: String,
    val password: String,
    val identityField: String
)