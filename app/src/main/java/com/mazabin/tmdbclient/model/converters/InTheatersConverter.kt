package com.mazabin.tmdbclient.model.converters

import com.mazabin.tmdbclient.model.InTheaters
import com.mazabin.tmdbclient.model.data.InTheatersData
import com.mazabin.tmdbclient.model.data.MovieData
import com.mazabin.tmdbclient.utils.convertToLocalDateObject
import javax.inject.Inject

class InTheatersConverter @Inject constructor(
    private val movieConverter: MovieConverterApi,
): InTheatersConverterApi {

    override fun convertInTheatersData(inTheatersData: InTheatersData) =
        InTheaters(
            fromDate = inTheatersData.dates.minimum.convertToLocalDateObject(),
            toDate = inTheatersData.dates.maximum.convertToLocalDateObject(),
            metaPageNumber = inTheatersData.page,
            metaTotalPages = inTheatersData.totalPages,
            movies = inTheatersData.results.map {
                convertMovieData(it)
            }
        )

    private fun convertMovieData(movieData: MovieData) =
        movieConverter.convertMovieData(movieData)
}