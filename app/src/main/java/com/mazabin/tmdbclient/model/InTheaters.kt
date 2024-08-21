package com.mazabin.tmdbclient.model

import java.time.LocalDate

data class InTheaters (
    val fromDate: LocalDate,
    var toDate: LocalDate,
    val metaPageNumber: Int,
    val metaTotalPages: Int,
    var movies: List<Movie>,
)