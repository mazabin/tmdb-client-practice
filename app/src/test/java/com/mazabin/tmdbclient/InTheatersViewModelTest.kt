package com.mazabin.tmdbclient

import com.mazabin.tmdbclient.favourites.FavouritesApi
import com.mazabin.tmdbclient.model.InTheaters
import com.mazabin.tmdbclient.network.InTheatersDataRepositoryApi
import com.mazabin.tmdbclient.viewmodels.InTheatersUiState
import com.mazabin.tmdbclient.viewmodels.InTheatersViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchException
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class InTheatersViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val mockToken = "Bearer token"
    private val mockLanguage = "en-US"
    private val mockDifferentLanguage = "pl-PL"
    private val mockPage = 1
    private val mockFavouritedMovieId = 123
    private val mockNotFavouritedMovieId = 456
    private val mockInTheaters = InTheaters(
        fromDate = LocalDate.of(2024, 2, 15),
        toDate = LocalDate.of(2024, 2, 18),
        metaPageNumber = 1,
        metaTotalPages = 5,
        movies = listOf(mockMovie, mockNotFavouritedMovie)
    )

    private val mockUserService: UserApi = mockk {
        every { getBearerToken() } returns mockToken
        every { getLanguage() } returns mockLanguage
    }

    private val mockFavouritesApi: FavouritesApi = mockk(relaxed = true) {
        coEvery { isMovieFavourited(mockFavouritedMovieId) } returns true
        coEvery { isMovieFavourited(mockNotFavouritedMovieId) } returns false
    }

    private val mockInTheatersDataRepository: InTheatersDataRepositoryApi = mockk {
        coEvery { getMoviesInTheaters(mockToken, mockLanguage, any()) } returns mockInTheaters
        coEvery { getMoviesInTheaters(mockToken, mockDifferentLanguage, any()) } throws Exception()
    }

    private val systemUnderTest = InTheatersViewModel(
        mockInTheatersDataRepository,
        mockUserService,
        mockFavouritesApi,
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `inTheatersDataRepository is called on data fetch`() = runTest(testDispatcher) {
            systemUnderTest.fetchData()

            coVerify(exactly = 1) {
                mockInTheatersDataRepository.getMoviesInTheaters(
                    mockToken,
                    mockLanguage,
                    mockPage,
                )
            }
        }

    @Test
    fun `isMovieFavourited is called on data fetch`() = runTest {
        systemUnderTest.fetchData()

        coVerify {
                mockFavouritesApi.isMovieFavourited(mockMovie.id)
                mockFavouritesApi.isMovieFavourited(mockNotFavouritedMovie.id)
            }
    }

    @Test
    fun `favouriteMovie is called when saveFavouriteState is called with newly favourited movie`() = runTest {
        systemUnderTest.saveFavouriteState(mockMovie)

        coVerify {
            mockFavouritesApi.favouriteMovie(mockFavouritedMovieId)
        }
    }

    @Test
    fun `unfavouriteMovie is called when saveFavouriteState is called with newly unfavourited movie`() = runTest {
        systemUnderTest.saveFavouriteState(mockNotFavouritedMovie)

        coVerify {
            mockFavouritesApi.unfavouriteMovie(mockNotFavouritedMovieId)
        }
    }

    @Test
    fun `inTheatersDataRepository is called when fetchNextPage is called and viemodel is in state Success`() = runTest {
        systemUnderTest.fetchData()

        systemUnderTest.fetchNextPage()

        assertThat(systemUnderTest.uiStateFlow.value).isInstanceOf(InTheatersUiState.Success::class.java)
        coVerify {
            mockInTheatersDataRepository.getMoviesInTheaters(
                mockToken,
                mockLanguage,
                mockPage,
            )

            mockInTheatersDataRepository.getMoviesInTheaters(
                mockToken,
                mockLanguage,
                mockPage + 1,
            )
        }
    }

    @Test
    fun `uiState is set to Error when fetching data fails`() = runTest {
        every { mockUserService.getLanguage() } returns mockDifferentLanguage

        systemUnderTest.fetchData()

        catchException {
            assertThat(systemUnderTest.uiStateFlow.value).isInstanceOf(InTheatersUiState.Error::class.java)
        }
    }
}