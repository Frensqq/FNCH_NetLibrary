package com.example.netlib.domain.model.Applicants

import kotlinx.serialization.Serializable

@Serializable
data class ApplicantsListResponse(
    val page:Int,
    val perPage:Int,
    val totalItems:Int,
    val totalPages: Int,
    val items: List<ApplicantsRecord>
)
