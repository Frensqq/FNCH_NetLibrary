package com.example.netlib.domain.model.Department

import kotlinx.serialization.Serializable

@Serializable

data class DepartmentsListResponse(
    val page:Int,
    val perPage:Int,
    val totalItems:Int,
    val totalPages: Int,
    val items: List<DepartmentsRecord>
)
