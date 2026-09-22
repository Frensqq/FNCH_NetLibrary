package com.example.netlib.domain.model.Department

import kotlinx.serialization.Serializable

@Serializable

data class DepartmentsRecord(
    val collectionId: String,
    val collectionName: String,
    val id: String,
    val name: String,
    val created: String,
    val updated: String,
)
