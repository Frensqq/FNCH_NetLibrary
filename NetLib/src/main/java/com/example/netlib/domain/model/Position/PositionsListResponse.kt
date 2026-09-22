package com.example.netlib.domain.model.Position

data class PositionsListResponse(
    val page:Int,
    val perPage:Int,
    val totalItems:Int,
    val totalPages: Int,
    val items: List<PositionsRecord>
)
