package com.mazabin.tmdbclient.favourites.datastore

import android.content.Context

interface FavouritesDataStoreApi {

    suspend fun isMovieFavourited(movieId: Int, context: Context): Boolean
    suspend fun addToFavourites(movieId: Int, context: Context)
    suspend fun removeFromFavourites(movieId: Int, context: Context)
}