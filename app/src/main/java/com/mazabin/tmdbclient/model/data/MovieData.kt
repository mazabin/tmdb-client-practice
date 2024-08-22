package com.mazabin.tmdbclient.model.data

import com.google.gson.annotations.SerializedName

data class MovieData(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("genres") val genres: List<GenreData>?,
    @SerializedName("origin_country") val originCountry: List<String>?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanyData>?,
)