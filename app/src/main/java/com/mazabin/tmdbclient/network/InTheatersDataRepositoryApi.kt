package com.mazabin.tmdbclient.network

import com.mazabin.tmdbclient.model.InTheaters

interface InTheatersDataRepositoryApi {

    suspend fun getMoviesInTheaters(
        authorizationHeader: String,
        language: String,
        page: Int,
    ): InTheaters
}