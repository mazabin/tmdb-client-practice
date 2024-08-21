package com.mazabin.tmdbclient.model.converters

import com.mazabin.tmdbclient.model.Movie
import com.mazabin.tmdbclient.model.data.MovieData

interface MovieConverterApi {

    fun convertMovieData(movieData: MovieData): Movie
}