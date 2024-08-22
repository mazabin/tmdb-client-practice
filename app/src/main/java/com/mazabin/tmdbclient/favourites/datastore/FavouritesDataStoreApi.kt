package com.mazabin.tmdbclient.favourites.datastore

interface FavouritesDataStoreApi {

    suspend fun isMovieFavourited(movieId: Int): Boolean
    suspend fun addToFavourites(movieId: Int)
    suspend fun removeFromFavourites(movieId: Int)
}