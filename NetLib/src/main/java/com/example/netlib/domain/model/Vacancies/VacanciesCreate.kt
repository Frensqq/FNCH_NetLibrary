package com.example.netlib.domain.model.Vacancies

import kotlinx.serialization.Serializable

@Serializable

data class VacanciesCreate(
    val title: String,
    val description: String,
    val requirements: String,
    val responsibilities: String,
    val conditions: String,
    val department: String,
    val position: String,
    val city: String,
    val status: String,
    val salaryFrom: Int,
    val salaryTo: Int,
    val author: String,
)
