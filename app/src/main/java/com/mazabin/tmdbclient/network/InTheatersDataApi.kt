package com.mazabin.tmdbclient.network

import com.mazabin.tmdbclient.model.data.InTheatersData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface InTheatersDataApi {

    @Headers("Content-Type: application/json")
    @GET("movie/now_playing")
    suspend fun getMoviesInTheaters(
        @Header("Authorization") authorizationHeader: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): InTheatersData
}