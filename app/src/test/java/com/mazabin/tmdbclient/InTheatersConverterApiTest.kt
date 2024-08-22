package com.mazabin.tmdbclient

import com.mazabin.tmdbclient.model.InTheaters
import com.mazabin.tmdbclient.model.converters.InTheatersConverter
import com.mazabin.tmdbclient.model.converters.MovieConverterApi
import com.mazabin.tmdbclient.model.data.DatesPeriodData
import com.mazabin.tmdbclient.model.data.InTheatersData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.time.LocalDate

class InTheatersConverterApiTest {

    private val mockInTheaters = InTheaters(
        fromDate = LocalDate.of(2024, 2, 15),
        toDate = LocalDate.of(2024, 2, 18),
        metaPageNumber = 1,
        metaTotalPages = 5,
        movies = listOf(mockMovie)
    )
    private val mockInTheatersData = InTheatersData(
        dates = DatesPeriodData(
            maximum = "2024-02-18",
            minimum = "2024-02-15",
        ),
        page = 1,
        results = listOf(mockMovieData),
        totalPages = 5,
        totalResults = 98
    )

    private val mockMovieConverterApi: MovieConverterApi = mockk {
        every { convertMovieData(mockMovieData) } returns mockMovie
    }

    private val systemUnderTest = InTheatersConverter(
        movieConverter = mockMovieConverterApi,
    )

    @Test
    fun `convertInTheatersData should return InTheaters object`() {
        val result = systemUnderTest.convertInTheatersData(mockInTheatersData)

        assertThat(result).isEqualTo(mockInTheaters)
    }

    @Test
    fun `movieConverter is called on conversion`() {
        systemUnderTest.convertInTheatersData(mockInTheatersData)

        verify {
            mockMovieConverterApi.convertMovieData(mockMovieData)
        }
    }
}