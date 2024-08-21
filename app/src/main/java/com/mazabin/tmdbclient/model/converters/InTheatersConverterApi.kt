package com.mazabin.tmdbclient.model.converters

import com.mazabin.tmdbclient.model.InTheaters
import com.mazabin.tmdbclient.model.data.InTheatersData

interface InTheatersConverterApi {

    fun convertInTheatersData(inTheatersData: InTheatersData): InTheaters
}