package com.example.netlib.domain.model.Applicants

data class ApplicantsRecord(
    val collectionId: String,
    val collectionName: String,
    val id: String,
    val lastName: String,
    val firstName: String,
    val patronymic: String,
    val phone: String,
    val email: String,
    val city: String,
    val status: String,
    val vacancy: String,
    val resume: String,
    val avatar: String,
    val source: String,
    val comment: String,
    val created: String,
    val updated: String,
)
