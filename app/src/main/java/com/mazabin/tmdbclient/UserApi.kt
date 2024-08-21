package com.mazabin.tmdbclient

interface UserApi {

    fun getBearerToken(): String
    fun getLanguage(): String
}