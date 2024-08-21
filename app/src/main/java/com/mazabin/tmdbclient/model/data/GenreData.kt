package com.mazabin.tmdbclient.model.data

import com.google.gson.annotations.SerializedName

data class GenreData(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)