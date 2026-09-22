package com.example.netlib.domain.model.CandidateCartComments

import kotlinx.serialization.Serializable

@Serializable

data class CandidateCardCommentsCreate(
    val name: String,
    val department: String,
)
