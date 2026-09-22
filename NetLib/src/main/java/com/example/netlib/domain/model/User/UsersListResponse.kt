package com.example.netlib.domain.model.User

import kotlinx.serialization.Serializable

@Serializable

data class UsersListResponse(
    val page:Int,
    val perPage:Int,
    val totalItems:Int,
    val totalPages: Int,
    val items: List<UsersRecord>
)
