package com.example.netlib.domain.model.ApplicantStatuses

import kotlinx.serialization.Serializable

@Serializable

data class ApplicantStatusesUpdate(
    val name: String,
    val color: String,
    val sort: String,
)
