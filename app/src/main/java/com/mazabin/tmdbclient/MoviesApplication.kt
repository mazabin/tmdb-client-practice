package com.mazabin.tmdbclient

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.DebugTree


@HiltAndroidApp
class MoviesApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(DebugTree()) //normally, it'd be configured to only log in debug mode, but I hadn't had time to do that
    }
}