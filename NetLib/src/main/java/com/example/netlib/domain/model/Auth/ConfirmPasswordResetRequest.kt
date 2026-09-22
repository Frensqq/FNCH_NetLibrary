package com.example.netlib.domain.model.Auth

data class ConfirmPasswordResetRequest(
    val token: String,
    val password: String,
    val passwordConfirm: String,
)
