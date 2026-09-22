package com.example.netlib.domain.model.User

import androidx.transition.Visibility
import kotlinx.serialization.Serializable

@Serializable

data class UsersRecord(
    val collectionId: String,
    val collectionName: String,
    val id: String,
    val email: String,
    val emailVisibility: String,
    val verified: String,
    val avatar: String,
    val firstName: String,
    val lastName: String,
    val patronymic: String,
    val phone: String,
    val department: String,
    val position: String,
    val role: String,
    val created: String,
    val updated: String,
)
