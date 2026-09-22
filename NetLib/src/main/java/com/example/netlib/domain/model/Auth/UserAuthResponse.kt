package com.example.netlib.domain.model.Auth

import com.example.netlib.domain.model.User.UsersRecord
import kotlinx.serialization.Serializable

@Serializable

data class UserAuthResponse(
    val token: String,
    val record: UsersRecord,
)
