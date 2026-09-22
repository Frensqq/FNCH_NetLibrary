package com.example.netlib.domain.model.Auth

data class ConfirmEmailChangeRequest(
    val toke: String,
    val password: String,
)
