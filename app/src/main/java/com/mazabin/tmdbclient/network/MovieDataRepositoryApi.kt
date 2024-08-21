package com.mazabin.tmdbclient.network

import com.mazabin.tmdbclient.model.Movie

interface MovieDataRepositoryApi {

    suspend fun getMovie(
        authorizationHeader: String,
        movieId: Int,
        language: String
    ): Movie
}