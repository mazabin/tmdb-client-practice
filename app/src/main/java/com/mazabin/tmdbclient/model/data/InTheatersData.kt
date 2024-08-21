package com.mazabin.tmdbclient.model.data

import com.google.gson.annotations.SerializedName

data class InTheatersData(
    @SerializedName("dates") val dates: DatesPeriodData,
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieData>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int,
)