package com.example.netlib.domain.model.CandidateStatuses

data class CandidateStatusesListResponse(
    val page:Int,
    val perPage:Int,
    val totalItems:Int,
    val totalPages: Int,
    val items: List<CandidateStatusesRecord>
)
