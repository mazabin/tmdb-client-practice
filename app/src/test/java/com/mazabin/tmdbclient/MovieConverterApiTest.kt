package com.mazabin.tmdbclient

import com.mazabin.tmdbclient.model.converters.MovieConverter
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MovieConverterApiTest {

    private val systemUnderTest = MovieConverter()

    @Test
    fun `all fields are properly converted`() {
        val result = systemUnderTest.convertMovieData(mockMovieData)

        assertThat(result).usingRecursiveComparison().isEqualTo(mockMovie)
    }

    @Test
    fun `null values in movieData are converted to default values`() {
        val result = systemUnderTest.convertMovieData(mockMovieDataWithNulls)

        assertThat(result).usingRecursiveComparison().isEqualTo(mockMovieWithDefaults)
    }
}