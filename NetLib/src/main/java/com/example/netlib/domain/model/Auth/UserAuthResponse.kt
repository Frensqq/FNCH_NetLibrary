package com.example.netlib.domain.model.Auth

import com.example.netlib.domain.model.User.UsersRecord

data class UserAuthResponse(
    val token: String,
    val record: UsersRecord,
)
