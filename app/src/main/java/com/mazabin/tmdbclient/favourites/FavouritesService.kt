package com.mazabin.tmdbclient.favourites

import com.mazabin.tmdbclient.favourites.datastore.FavouritesDataStoreApi
import javax.inject.Inject

class FavouritesService @Inject constructor(
    private val favouritesDataStore: FavouritesDataStoreApi,
) : FavouritesApi {

    override suspend fun isMovieFavourited(movieId: Int) =
        favouritesDataStore.isMovieFavourited(movieId)

    override suspend fun favouriteMovie(movieId: Int) {
        favouritesDataStore.addToFavourites(movieId)
    }

    override suspend fun unfavouriteMovie(movieId: Int) {
        favouritesDataStore.removeFromFavourites(movieId)
    }
}