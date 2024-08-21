package com.mazabin.tmdbclient.network

import retrofit2.Retrofit
import javax.inject.Inject

class MovieDataRepository @Inject constructor(
    private val retrofit: Retrofit,
) : MovieDataRepositoryApi {

    override suspend fun getMovie(
        authorizationHeader: String,
        movieId: Int,
        language: String,
    ) =
        retrofit.create(getClassName()).getMovieDetails(
            authorizationHeader = authorizationHeader,
            movieId = movieId,
            language = language,
        )


    private fun getClassName() = MovieDataApi::class.java
}