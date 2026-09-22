package com.example.netlib.domain.model.Vacancies

data class VacanciesUpdate(
    val title: String,
    val description: String,
    val requirements: String,
    val responsibilities: String,
    val conditions: String,
    val department: String,
    val position: String,
    val city: String,
    val status: String,
    val salaryFrom: Number,
    val salaryTo: Number,
    val author: String,
)
