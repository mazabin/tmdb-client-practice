package com.mazabin.tmdbclient.model

import java.time.LocalDate

data class InTheaters (
    val fromDate: LocalDate,
    val toDate: LocalDate,
    val metaPageNumber: Int,
    val metaTotalPages: Int,
    val movies: List<Movie>,
)