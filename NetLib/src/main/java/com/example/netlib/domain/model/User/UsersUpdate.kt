package com.example.netlib.domain.model.User

import kotlinx.serialization.Serializable

@Serializable

data class UsersUpdate(
    val email: String,
    val emailVisibility: Boolean,
    val firstName: String,
    val lastName: String,
    val patronymic: String,
    val phone: String,
    val department: String,
    val position: String,
    val role: String,
)

@Serializable
data class UsersUpdatePass(
    val email: String,
    val oldPassword: String,
    val password: String,
    val passwordConfirm: String
)
