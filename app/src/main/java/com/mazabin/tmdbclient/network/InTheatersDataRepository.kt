package com.mazabin.tmdbclient.network

import com.mazabin.tmdbclient.model.converters.InTheatersConverterApi
import retrofit2.Retrofit
import javax.inject.Inject

class InTheatersDataRepository @Inject constructor(
    private val retrofit: Retrofit,
    private val inTheatersConverter: InTheatersConverterApi,
) : InTheatersDataRepositoryApi {

    override suspend fun getMoviesInTheaters(
        authorizationHeader: String,
        language: String,
        page: Int,
    ) = inTheatersConverter.convertInTheatersData(
            fetchData(
                authorizationHeader = authorizationHeader,
                language = language,
                page = page,
            )
    )

    private suspend fun fetchData(
        authorizationHeader: String,
        language: String,
        page: Int,
    ) = createRetrofit().getMoviesInTheaters(
            authorizationHeader = authorizationHeader,
            language = language,
            page = page,
        )

    private fun getClassName() = InTheatersDataApi::class.java

    private fun createRetrofit() = retrofit.create(getClassName())
}