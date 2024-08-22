package com.mazabin.tmdbclient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mazabin.tmdbclient.UserApi
import com.mazabin.tmdbclient.favourites.FavouritesApi
import com.mazabin.tmdbclient.model.InTheaters
import com.mazabin.tmdbclient.model.Movie
import com.mazabin.tmdbclient.network.InTheatersDataRepositoryApi
import com.mazabin.tmdbclient.viewmodels.InTheatersUiState.Error
import com.mazabin.tmdbclient.viewmodels.InTheatersUiState.Loading
import com.mazabin.tmdbclient.viewmodels.InTheatersUiState.Success
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

    private val _uiState = MutableStateFlow<InTheatersUiState>(Loading)
    val uiStateFlow = _uiState.asStateFlow()

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            try {
                setUiStateToSuccess(fetchInTheaters())
            } catch (exception: Exception) {
                Timber
                    .tag(this.javaClass.name)
                    .e(exception)

                setUiStateToError(exception.message.orEmpty())
            }
        }
    }

    suspend fun saveFavouriteState(movie: Movie) {
        with(movie) {
            if (isFavourited) {
                favouritesApi.favouriteMovie(id)
            } else {
                favouritesApi.unfavouriteMovie(id)
            }
            updateMovieInState(this)
        }
    }

    suspend fun fetchNextPage() {
        val nextPage = fetchInTheaters()
        val newInTheaters = nextPage.copy(
            movies = currentInTheaters().movies + nextPage.movies
        )
        _uiState.value = (uiStateFlow.value as Success).copy(inTheaters = newInTheaters)
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

    private fun getPage(): Int {
        if (uiStateFlow.value is Success) {
            with(currentInTheaters()) {
                if (metaPageNumber < metaTotalPages) {
                    return metaPageNumber + 1
                }
                return metaPageNumber
            }
        }
        return 1
    }

    private fun updateMovieInState(movie: Movie) {
        val mutableMovies = currentInTheaters().movies.toMutableList()
        val index = mutableMovies.indexOfLast { it.id == movie.id }
        mutableMovies[index] = movie
        val newMovies = mutableMovies.toList()

        val updatedInTheaters = currentInTheaters().copy(movies = newMovies)

        setUiStateToSuccess(updatedInTheaters)
    }

    private fun currentInTheaters() = (uiStateFlow.value as Success).inTheaters

    private fun setUiStateToError(error: String) {
        _uiState.value = Error(error)
    }

    private fun setUiStateToSuccess(inTheaters: InTheaters) {
        _uiState.value = Success(inTheaters)
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
