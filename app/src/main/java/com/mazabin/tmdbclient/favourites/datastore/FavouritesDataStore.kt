package com.mazabin.tmdbclient.favourites.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.mazabin.tmdbclient.Favourites
import kotlinx.coroutines.flow.first
import javax.inject.Inject


private const val DATA_STORE_FILE_NAME = "favourites.store_pb"
private val Context.favouritesDataStore: DataStore<Favourites> by dataStore(
    fileName = DATA_STORE_FILE_NAME,
    serializer = FavouritesSerializer,
)

class FavouritesDataStore @Inject constructor(
    private val context: Context
) : FavouritesDataStoreApi {

    override suspend fun isMovieFavourited(movieId: Int, context2: Context) =
        context.favouritesDataStore.data.first().idList.contains(movieId)

    override suspend fun addToFavourites(movieId: Int, context2: Context) {
        context.favouritesDataStore.updateData { currentFavourites ->
            currentFavourites
                .toBuilder()
                .addId(movieId)
                .build()
        }
    }

    override suspend fun removeFromFavourites(movieId: Int, context2: Context) {
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