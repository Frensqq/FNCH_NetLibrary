package com.example.netlib.domain.model.CandidateCartComments

import kotlinx.serialization.Serializable

@Serializable

data class CandidateCardCommentsRecord(
    val collectionId: String,
    val collectionName: String,
    val id: String,
    val card: String,
    val text: String,
    val author: String,
    val created: String,
    val updated: String,
)
