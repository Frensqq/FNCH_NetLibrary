package com.example.netlib.domain.model.ApplicantStatuses

data class ApplicantsStatusesListResponse(
    val page:Int,
    val perPage:Int,
    val totalItems:Int,
    val totalPages: Int,
    val items: List<ApplicantStatusesRecord>
)
