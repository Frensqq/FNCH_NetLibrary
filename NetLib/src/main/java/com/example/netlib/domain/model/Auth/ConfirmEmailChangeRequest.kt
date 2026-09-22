package com.example.netlib.domain.model.Auth

import kotlinx.serialization.Serializable

@Serializable

data class ConfirmEmailChangeRequest(
    val toke: String,
    val password: String,
)
