package com.example.netlib.domain.model.Position

import kotlinx.serialization.Serializable

@Serializable

data class PositionsCreate(
    val name: String,
    val department: String,
)
