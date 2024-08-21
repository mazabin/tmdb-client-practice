package com.mazabin.tmdbclient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mazabin.tmdbclient.UserApi
import com.mazabin.tmdbclient.favourites.FavouritesApi
import com.mazabin.tmdbclient.model.InTheaters
import com.mazabin.tmdbclient.model.Movie
import com.mazabin.tmdbclient.network.InTheatersDataRepositoryApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class InTheatersViewModel @Inject constructor(
    private val inTheatersDataRepository: InTheatersDataRepositoryApi,
    private val userService: UserApi,
    private val favouritesApi: FavouritesApi,
): ViewModel() {

    private val _uiState = MutableStateFlow<InTheatersUiState>(InTheatersUiState.Loading)
    val uiStateFlow = _uiState.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                fetchInTheaters()
                    .let {
                        _uiState.value = InTheatersUiState.Success(
                            inTheaters = it,
                        )
                    }
            } catch (exception: Exception) {
                Timber
                    .tag(this.javaClass.name)
                    .e(exception)

                _uiState.value = InTheatersUiState.Error(
                    error = exception.message.orEmpty(),
                )
            }

        }
    }

    suspend fun saveFavouriteState(movie: Movie) {
        if (movie.isFavourited) {
            favouritesApi.favouriteMovie(movie.id)
        } else {
            favouritesApi.unfavouriteMovie(movie.id)
        }
        updateMovieInState(movie)
    }

    private suspend fun fetchInTheaters() =
        inTheatersDataRepository.getMoviesInTheaters(
            userService.getBearerToken(),
            userService.getLanguage(),
            getPage(),
        ).also { updateMovieFavourites(it) }

    private suspend fun updateMovieFavourites(inTheaters: InTheaters) {
        for (movie in inTheaters.movies) {
            movie.isFavourited = favouritesApi.isMovieFavourited(movie.id)
        }
    }

    suspend fun fetchNextPage() {
        val nextPage = fetchInTheaters()
        val currentInTheaters = (uiStateFlow.value as InTheatersUiState.Success).inTheaters
        val newInTheaters = nextPage.copy(
            movies = currentInTheaters.movies + nextPage.movies
        )
        _uiState.value = (uiStateFlow.value as InTheatersUiState.Success).copy(inTheaters = newInTheaters)
    }


    private fun getPage(): Int {
        if (uiStateFlow.value is InTheatersUiState.Success) {
            val lastCollection = (uiStateFlow.value as InTheatersUiState.Success).inTheaters
            val currentPage = lastCollection.metaPageNumber
            val maxPage = lastCollection.metaTotalPages
            if (currentPage < maxPage) {
                return currentPage + 1
            }
            return currentPage
        }
        return 1
    }

    private fun updateMovieInState(movie: Movie) {
        val inTheaters = (uiStateFlow.value as InTheatersUiState.Success).inTheaters
        val mutableMovies = inTheaters.movies.toMutableList()
        val index = mutableMovies.indexOfLast { it.id == movie.id }
        mutableMovies[index] = movie
        val newMovies = mutableMovies.toList()

        val updatedInTheaters = inTheaters.copy(movies = newMovies)

        _uiState.value = InTheatersUiState.Success(updatedInTheaters)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val inTheatersDataRepository: InTheatersDataRepositoryApi,
        private val userService: UserApi,
        private val favouritesApi: FavouritesApi,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InTheatersViewModel::class.java)) {
                return InTheatersViewModel(
                    inTheatersDataRepository,
                    userService,
                    favouritesApi,
                ) as T
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

sealed class InTheatersUiState {
    object Loading : InTheatersUiState()
    data class Success(val inTheaters: InTheaters) : InTheatersUiState()
    data class Error(val error: String) : InTheatersUiState()
}
