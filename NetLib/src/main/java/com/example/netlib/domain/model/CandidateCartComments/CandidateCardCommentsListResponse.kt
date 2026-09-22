package com.example.netlib.domain.model.CandidateCartComments

data class CandidateCardCommentsListResponse(
    val page:Int,
    val perPage:Int,
    val totalItems:Int,
    val totalPages: Int,
    val items: List<CandidateCardCommentsRecord>
)
