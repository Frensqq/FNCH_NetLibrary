package com.example.netlib.domain.model.Vacancies

data class VacanciesListResponse(
    val page:Int,
    val perPage:Int,
    val totalItems:Int,
    val totalPages: Int,
    val items: List<VacanciesRecord>
)
