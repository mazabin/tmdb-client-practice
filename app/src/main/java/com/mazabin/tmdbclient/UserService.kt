package com.mazabin.tmdbclient

import android.content.Context
import javax.inject.Inject

//Not production-ready solution and not acceptable in real life code. Normally we'd get a token from the backend on positive authentication.
//Any secrets we'd need to store in codebase, we'd probably hide away in keystore. I don't have the time here to do all of this properly, so one shortcut for the project.
private const val BEARER_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2ZTM1OTcxYjM2OWFjZmZjYjJkNGE3MmQ2ZDgzYTkwNiIsIm5iZiI6MTcyNDA5MjYzNC40NTgyNzYsInN1YiI6IjVhOGMwZWZkOTI1MTQxMGExZjA4YTg4YiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.qkciDncglLAtKbKtqc8Ge9mYYjKfRU0AIj4U4Yuujds"

class UserService @Inject constructor(
    private val context: Context
) : UserApi {

    override fun getBearerToken() = BEARER_TOKEN

    override fun getLanguage() =
        context.resources.configuration.locales[0].toString()
}