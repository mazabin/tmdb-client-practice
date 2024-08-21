package com.mazabin.tmdbclient.favourites

interface FavouritesApi {

    suspend fun isMovieFavourited(movieId: Int): Boolean
    suspend fun favouriteMovie(movieId: Int)
    suspend fun unfavouriteMovie(movieId: Int)
}
