package com.example.netlib.domain.model.CandidateStatuses

import kotlinx.serialization.Serializable

@Serializable

data class CandidateStatusesUpdate(
    val card: String,
    val text: String,
    val author: String,
)
