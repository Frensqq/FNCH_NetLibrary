package com.example.netlib.domain.model.Department

import kotlinx.serialization.Serializable

@Serializable

data class DepartmentsCreate(
    val name: String,
)
