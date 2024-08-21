package com.mazabin.tmdbclient.network

import com.mazabin.tmdbclient.model.data.MovieData

interface MovieDataRepositoryApi {

    suspend fun getMovie(
        authorizationHeader: String,
        movieId: Int,
        language: String
    ): MovieData
}