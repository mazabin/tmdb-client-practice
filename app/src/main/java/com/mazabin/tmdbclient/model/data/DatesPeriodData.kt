package com.mazabin.tmdbclient.model.data

import com.google.gson.annotations.SerializedName

data class DatesPeriodData(
    @SerializedName("maximum") val maximum: String,
    @SerializedName("minimum") val minimum: String,
)
