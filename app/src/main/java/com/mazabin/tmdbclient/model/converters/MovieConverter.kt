package com.mazabin.tmdbclient.model.converters

import com.mazabin.tmdbclient.model.Movie
import com.mazabin.tmdbclient.model.data.MovieData
import com.mazabin.tmdbclient.utils.convertToLocalDateObject
import javax.inject.Inject

class MovieConverter @Inject constructor(): MovieConverterApi {

    override fun convertMovieData(
        movieData: MovieData
    ) = Movie(
        id = movieData.id,
        title = movieData.title,
        originalTitle = movieData.originalTitle,
        originalLanguage = movieData.originalLanguage,
        overview = movieData.overview,
        backdropPath = movieData.backdropPath.orEmpty(),
        posterPath = movieData.posterPath.orEmpty(),
        adult = movieData.adult,
        releaseDate = movieData.releaseDate.convertToLocalDateObject(),
        popularity = movieData.popularity,
        voteAverage = movieData.voteAverage,
        voteCount = movieData.voteCount,
        genres = movieData.genres?.map { it.name }.orEmpty(),
        originCountry = movieData.originCountry.orEmpty(),
        runtime = movieData.runtime ?: 0,
        productionCompanies = movieData.productionCompanies?.map { it.name }.orEmpty(),
    )
}