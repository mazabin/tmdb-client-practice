package com.mazabin.tmdbclient.model

import java.time.LocalDate

class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val originalLanguage: String,
    val overview: String,
    val backdropPath: String,
    val posterPath: String,
    val adult: Boolean,
    val releaseDate: LocalDate,
    val popularity: Double,
    val voteAverage: Double,
    val voteCount: Int,
    val genres: List<String>,
    val originCountry: List<String>,
    val runtime: Int?,
    val productionCompanies: List<String>,
    var isFavourited: Boolean = false,
)