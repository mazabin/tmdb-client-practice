package com.mazabin.tmdbclient.favourites

import android.content.Context
import com.mazabin.tmdbclient.favourites.datastore.FavouritesDataStoreApi
import javax.inject.Inject

class FavouritesService @Inject constructor(
    private val context: Context,
    private val favouritesDataStore: FavouritesDataStoreApi,
) : FavouritesApi {

    override suspend fun isMovieFavourited(movieId: Int) =
        favouritesDataStore.isMovieFavourited(movieId, context)

    override suspend fun favouriteMovie(movieId: Int) {
        favouritesDataStore.addToFavourites(movieId, context)
    }

    override suspend fun unfavouriteMovie(movieId: Int) {
        favouritesDataStore.removeFromFavourites(movieId, context)
    }
}