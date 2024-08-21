package com.mazabin.tmdbclient.network

import com.mazabin.tmdbclient.model.converters.MovieConverterApi
import retrofit2.Retrofit
import javax.inject.Inject

class MovieDataRepository @Inject constructor(
    private val retrofit: Retrofit,
    private val movieDataConverter: MovieConverterApi,
) : MovieDataRepositoryApi {

    override suspend fun getMovie(
        authorizationHeader: String,
        movieId: Int,
        language: String,
    ) = movieDataConverter.convertMovieData(
        fetchData(
            authorizationHeader = authorizationHeader,
            movieId = movieId,
            language = language,
        )
    )

    private suspend fun fetchData(
        authorizationHeader: String,
        movieId: Int,
        language: String,
    ) = createRetrofit().getMovieDetails(
        authorizationHeader = authorizationHeader,
        movieId = movieId,
        language = language,
    )

    private fun getClassName() = MovieDataApi::class.java

    private fun createRetrofit() = retrofit.create(getClassName())
}