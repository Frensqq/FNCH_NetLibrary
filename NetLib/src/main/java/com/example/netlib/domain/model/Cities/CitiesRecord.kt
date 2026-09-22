package com.example.netlib.domain.model.Cities

import kotlinx.serialization.Serializable

@Serializable

data class CitiesRecord(
    val collectionId: String,
    val collectionName: String,
    val id: String,
    val name: String,
    val created: String,
    val updated: String,
)