package com.example.netlib.domain.model.User

import kotlinx.serialization.Serializable

@Serializable

data class UsersCreate(
    val email: String,
    val emailVisibility: String,
    val verified: String,
    val firstName: String,
    val lastName: String,
    val patronymic: String,
    val phone: String,
    val department: String,
    val position: String,
    val role: String,
    val password: String,
    val confirmPassword: String
)
