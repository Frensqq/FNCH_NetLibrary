package com.example.netlib.domain.model.CandidateCartComments

import kotlinx.serialization.Serializable

@Serializable

data class CandidateCardCommentsCreate(
    val card: String,
    val text: String,
    val author: String,
)
