package com.mazabin.tmdbclient.favourites.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.mazabin.tmdbclient.Favourites
import kotlinx.coroutines.flow.first
import javax.inject.Inject


private const val DATA_STORE_FILE_NAME = "favourites.store_pb"

class FavouritesDataStore @Inject constructor() : FavouritesDataStoreApi {

    private val Context.favouritesDataStore: DataStore<Favourites> by dataStore(
        fileName = DATA_STORE_FILE_NAME,
        serializer = FavouritesSerializer,
    )

    override suspend fun isMovieFavourited(movieId: Int, context: Context) =
        context.favouritesDataStore.data.first().idList.contains(movieId)

    override suspend fun addToFavourites(movieId: Int, context: Context) {
        context.favouritesDataStore.updateData { currentFavourites ->
            currentFavourites
                .toBuilder()
                .addId(movieId)
                .build()
        }
    }

    override suspend fun removeFromFavourites(movieId: Int, context: Context) {
        if (context.favouritesDataStore.data.first().idList.contains(movieId)) {
            context.favouritesDataStore.updateData { currentFavourites ->
                val newFavourites = currentFavourites.idList.filterNot { it == movieId }
                currentFavourites
                    .toBuilder()
                    .clearId()
                    .addAllId(newFavourites)
                    .build()
            }
        }
    }
}

