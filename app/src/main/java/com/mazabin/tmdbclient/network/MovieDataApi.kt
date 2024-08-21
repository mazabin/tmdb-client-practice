package com.mazabin.tmdbclient.network

import com.mazabin.tmdbclient.model.data.MovieData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieDataApi {

    @Headers("Content-Type: application/json")
    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Header("Authorization") authorizationHeader: String,
        @Path("movieId") movieId: Int,
        @Query("language") language: String,
    ): MovieData
}