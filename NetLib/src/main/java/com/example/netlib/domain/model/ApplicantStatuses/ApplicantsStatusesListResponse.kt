package com.example.netlib.domain.model.ApplicantStatuses

import kotlinx.serialization.Serializable

@Serializable

data class ApplicantsStatusesListResponse(
    val page:Int,
    val perPage:Int,
    val totalItems:Int,
    val totalPages: Int,
    val items: List<ApplicantStatusesRecord>
)
