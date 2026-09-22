package com.example.netlib.domain.model.CandidateCards

data class CandidateCardsUpdate(
    val vacancy: String,
    val applicant: String,
    val status: String,
    val hrResponsible: String,
    val sort: String,
    val isDeleted: String,
)
