package com.example.netlib.domain.model.CandidateCartComments

import kotlinx.serialization.Serializable

@Serializable

data class CandidateCardCommentsUpdate(
    val card: String,
    val text: String,
    val author: String,
)
