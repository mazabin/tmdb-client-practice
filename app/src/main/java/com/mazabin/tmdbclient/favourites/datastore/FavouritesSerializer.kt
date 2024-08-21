package com.mazabin.tmdbclient.favourites.datastore

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.mazabin.tmdbclient.Favourites
import java.io.InputStream
import java.io.OutputStream

object FavouritesSerializer: Serializer<Favourites> {
    override val defaultValue: Favourites = Favourites.getDefaultInstance()
    override suspend fun readFrom(input: InputStream): Favourites {
        try {
            return Favourites.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Favourites, output: OutputStream) = t.writeTo(output)
}
