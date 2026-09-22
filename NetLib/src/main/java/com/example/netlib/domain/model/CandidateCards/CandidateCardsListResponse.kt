package com.example.netlib.domain.model.CandidateCards

import kotlinx.serialization.Serializable

@Serializable

data class CandidateCardsListResponse(
    val page:Int,
    val perPage:Int,
    val totalItems:Int,
    val totalPages: Int,
    val items: List<CandidateCardsRecord>
)
