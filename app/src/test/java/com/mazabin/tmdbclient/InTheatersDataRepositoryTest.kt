package com.mazabin.tmdbclient

import com.mazabin.tmdbclient.model.InTheaters
import com.mazabin.tmdbclient.model.converters.InTheatersConverterApi
import com.mazabin.tmdbclient.model.data.DatesPeriodData
import com.mazabin.tmdbclient.model.data.InTheatersData
import com.mazabin.tmdbclient.model.data.MovieData
import com.mazabin.tmdbclient.network.InTheatersDataApi
import com.mazabin.tmdbclient.network.InTheatersDataRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import retrofit2.Retrofit

class InTheatersDataRepositoryTest {

    private val mockInTheaters: InTheaters = mockk()
    private val mockMovieData: MovieData = mockk()
    private val mockInTheatersData = InTheatersData(
        dates = DatesPeriodData(
            maximum = "2024-02-15",
            minimum = "2024-02-18",
        ),
        page = 1,
        results = listOf(mockMovieData),
        totalPages = 5,
        totalResults = 98
    )
    private val mockInTheatersDataApi: InTheatersDataApi = mockk {
        coEvery { getMoviesInTheaters(any(), any(), any()) } returns mockInTheatersData
    }
    private val mockRetrofit: Retrofit = mockk {
        every { create(any<Class<InTheatersDataApi>>()) } returns mockInTheatersDataApi
    }
    private val mockInTheatersConverter: InTheatersConverterApi = mockk(relaxed = true) {
        every { convertInTheatersData(mockInTheatersData) } returns mockInTheaters
    }

    private val systemUnderTest = InTheatersDataRepository(
        retrofit = mockRetrofit,
        inTheatersConverter = mockInTheatersConverter,
    )

    @Test
    fun `getMoviesInTheaters should return InTheaters object`() = runTest {
        val result = systemUnderTest.getMoviesInTheaters(
            authorizationHeader = "Bearer token",
            language = "en-US",
            page = 1,
        )

        assertThat(result).isEqualTo(mockInTheaters)
    }

    @Test
    fun `converter is called after data fetch`()  = runTest {
        systemUnderTest.getMoviesInTheaters(
            authorizationHeader = "Bearer token",
            language = "en-US",
            page = 1,
        )

        verify(exactly = 1) {
            mockInTheatersConverter.convertInTheatersData(mockInTheatersData)
        }
    }

    @Test
    fun `retrofit for the data repository is created on data fetch`()  = runTest {
        systemUnderTest.getMoviesInTheaters(
            authorizationHeader = "Bearer token",
            language = "en-US",
            page = 1,
        )

        verify(exactly = 1) {
            mockRetrofit.create(any<Class<InTheatersDataApi>>())
        }
    }

    @Test
    fun `data api is called on data fetch`()  = runTest {
        val expectedToken = "Bearer token"
        val expectedLanguage = "en-US"
        val expectedPage = 1

        systemUnderTest.getMoviesInTheaters(
            authorizationHeader = "Bearer token",
            language = "en-US",
            page = 1,
        )

        coVerify(exactly = 1) {
            mockInTheatersDataApi.getMoviesInTheaters(
                expectedToken,
                expectedLanguage,
                expectedPage,
            )
        }

        verify(exactly = 1) {
            mockRetrofit.create(any<Class<InTheatersDataApi>>())
        }
    }
}