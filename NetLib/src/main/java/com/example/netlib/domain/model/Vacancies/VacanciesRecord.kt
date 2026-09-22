package com.example.netlib.domain.model.Vacancies

import kotlinx.serialization.Serializable

@Serializable

data class VacanciesRecord(
    val collectionId: String,
    val collectionName: String,
    val id: String,
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
    val files: String,
    val created: String,
    val updated: String,
)
