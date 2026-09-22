package com.example.netlib.domain.model.Auth

import kotlinx.serialization.Serializable

@Serializable

data class ConfirmPasswordResetRequest(
    val token: String,
    val password: String,
    val passwordConfirm: String,
)
