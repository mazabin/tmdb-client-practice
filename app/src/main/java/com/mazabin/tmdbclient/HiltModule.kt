package com.mazabin.tmdbclient

import android.app.Application
import android.content.Context
import com.mazabin.tmdbclient.favourites.FavouritesApi
import com.mazabin.tmdbclient.favourites.FavouritesService
import com.mazabin.tmdbclient.favourites.datastore.FavouritesDataStore
import com.mazabin.tmdbclient.favourites.datastore.FavouritesDataStoreApi
import com.mazabin.tmdbclient.model.converters.InTheatersConverter
import com.mazabin.tmdbclient.model.converters.InTheatersConverterApi
import com.mazabin.tmdbclient.model.converters.MovieConverter
import com.mazabin.tmdbclient.model.converters.MovieConverterApi
import com.mazabin.tmdbclient.network.InTheatersDataRepository
import com.mazabin.tmdbclient.network.InTheatersDataRepositoryApi
import com.mazabin.tmdbclient.network.MovieDataRepository
import com.mazabin.tmdbclient.network.MovieDataRepositoryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    fun provideUserApi(userService: UserService): UserApi = userService

    @Provides
    fun provideInTheatersDataRepositoryApi(inTheatersDataRepository: InTheatersDataRepository): InTheatersDataRepositoryApi =
        inTheatersDataRepository

    @Provides
    fun provideMovieDataRepositoryApi(movieDataRepository: MovieDataRepository): MovieDataRepositoryApi =
        movieDataRepository

    @Provides
    fun provideInTheatersConverterApi(inTheatersConverter: InTheatersConverter): InTheatersConverterApi =
        inTheatersConverter

    @Provides
    fun provideMovieConverterApi(movieConverter: MovieConverter): MovieConverterApi = movieConverter

    @Provides
    fun provideFavouritesApi(favouritesService: FavouritesService): FavouritesApi =
        favouritesService

    @Provides
    fun provideFavouritesDataStoreApi(favouritesDataStore: FavouritesDataStore): FavouritesDataStoreApi =
        favouritesDataStore

    @Provides
    fun provideApplicationContext(application: Application): Context = application
}