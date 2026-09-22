package com.example.netlib.domain.model.CandidateCartComments

import kotlinx.serialization.Serializable

@Serializable

data class CandidateCardCommentsListResponse(
    val page:Int,
    val perPage:Int,
    val totalItems:Int,
    val totalPages: Int,
    val items: List<CandidateCardCommentsRecord>
)
