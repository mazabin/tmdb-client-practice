package com.mazabin.tmdbclient.network

import com.mazabin.tmdbclient.model.data.MovieData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface MovieDataApi {
    //https://api.themoviedb.org/3/movie/{movie_id}
    @Headers("Content-Type: application/json")
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Header("Authorization") authorizationHeader: String,
        @Query("movieId") movieId: Int,
        @Query("language") language: String,
    ): MovieData
}