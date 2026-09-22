package com.example.netlib.domain.model.CandidateCards

import kotlinx.serialization.Serializable

@Serializable

data class CandidateCardsRecord(
    val collectionId: String,
    val collectionName: String,
    val id: String,
    val vacancy: String,
    val applicant: String,
    val status: String,
    val hrResponsible: String,
    val sort: String,
    val isDeleted: String,
    val created: String,
    val updated: String,
)
