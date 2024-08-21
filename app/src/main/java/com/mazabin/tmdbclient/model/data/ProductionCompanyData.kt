package com.mazabin.tmdbclient.model.data

import com.google.gson.annotations.SerializedName

data class ProductionCompanyData(
    @SerializedName("name") val name: String,
)
