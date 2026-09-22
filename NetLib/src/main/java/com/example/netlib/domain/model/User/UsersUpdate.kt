package com.example.netlib.domain.model.User

data class UsersUpdate(
    val id: String,
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
    val oldPassword: String,
    val password: String,
    val confirmPassword: String
)
