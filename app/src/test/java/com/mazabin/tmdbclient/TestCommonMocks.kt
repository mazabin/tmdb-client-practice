package com.mazabin.tmdbclient

import com.mazabin.tmdbclient.model.Movie
import com.mazabin.tmdbclient.model.data.GenreData
import com.mazabin.tmdbclient.model.data.MovieData
import com.mazabin.tmdbclient.model.data.ProductionCompanyData
import java.time.LocalDate

val mockMovieWithDefaults = Movie(
    id = 123,
    title = "The Shawshank Redemption",
    originalTitle = "The Shawshank Redemption",
    originalLanguage = "en",
    overview = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
    backdropPath = "",
    posterPath = "",
    adult = true,
    releaseDate = LocalDate.of(1994, 9, 23),
    popularity = 85.399,
    voteAverage = 8.7,
    voteCount = 22842,
    genres = emptyList(),
    originCountry = emptyList(),
    runtime = 0,
    productionCompanies = emptyList(),
    isFavourited = false,
)
val mockMovie = Movie(
    id = 123,
    title = "The Shawshank Redemption",
    originalTitle = "The Shawshank Redemption",
    originalLanguage = "en",
    overview = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
    backdropPath = "/kGzFbGhp99zva6oZODW5atUtnqi.jpg",
    posterPath = "/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg",
    adult = true,
    releaseDate = LocalDate.of(1994, 9, 23),
    popularity = 85.399,
    voteAverage = 8.7,
    voteCount = 22842,
    genres = listOf("Drama", "Crime"),
    originCountry = listOf("US"),
    runtime = 142,
    productionCompanies = listOf("Castle Rock Entertainment", "Columbia Pictures"),
    isFavourited = false,
)
val mockNotFavouritedMovie = Movie(
    id = 456,
    title = "The Shawshank Redemption",
    originalTitle = "The Shawshank Redemption",
    originalLanguage = "en",
    overview = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
    backdropPath = "/kGzFbGhp99zva6oZODW5atUtnqi.jpg",
    posterPath = "/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg",
    adult = true,
    releaseDate = LocalDate.of(1994, 9, 23),
    popularity = 85.399,
    voteAverage = 8.7,
    voteCount = 22842,
    genres = listOf("Drama", "Crime"),
    originCountry = listOf("US"),
    runtime = 142,
    productionCompanies = listOf("Castle Rock Entertainment", "Columbia Pictures"),
    isFavourited = false,
)
val mockMovieData = MovieData(
    id = 123,
    title = "The Shawshank Redemption",
    originalTitle = "The Shawshank Redemption",
    originalLanguage = "en",
    overview = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
    posterPath = "/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg",
    backdropPath = "/kGzFbGhp99zva6oZODW5atUtnqi.jpg",
    adult = true,
    popularity = 85.399,
    releaseDate = "1994-09-23",
    voteAverage = 8.7,
    voteCount = 22842,
    genres = listOf(
        GenreData(18, "Drama"),
        GenreData(80, "Crime")
    ),
    originCountry = listOf("US"),
    runtime = 142,
    productionCompanies = listOf(
        ProductionCompanyData("Castle Rock Entertainment"),
        ProductionCompanyData("Columbia Pictures"),
    ),
)
val mockMovieDataWithNulls = MovieData(
    id = 123,
    title = "The Shawshank Redemption",
    originalTitle = "The Shawshank Redemption",
    originalLanguage = "en",
    overview = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
    posterPath = null,
    backdropPath = null,
    adult = true,
    popularity = 85.399,
    releaseDate = "1994-09-23",
    voteAverage = 8.7,
    voteCount = 22842,
    genres = null,
    originCountry = null,
    runtime = null,
    productionCompanies = null,
)