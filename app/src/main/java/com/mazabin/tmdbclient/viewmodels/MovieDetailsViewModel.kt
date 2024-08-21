package com.mazabin.tmdbclient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mazabin.tmdbclient.UserApi
import com.mazabin.tmdbclient.favourites.FavouritesApi
import com.mazabin.tmdbclient.model.Movie
import com.mazabin.tmdbclient.network.MovieDataRepositoryApi
import com.mazabin.tmdbclient.viewmodels.MovieDetailsUiState.Error
import com.mazabin.tmdbclient.viewmodels.MovieDetailsUiState.Loading
import com.mazabin.tmdbclient.viewmodels.MovieDetailsUiState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDataRepository: MovieDataRepositoryApi,
    private val userService: UserApi,
    private val favouritesApi: FavouritesApi,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailsUiState>(Loading)
    val uiStateFlow = _uiState.asStateFlow()

    fun fetchData(movieId: Int) {
        _uiState.value = Loading
        viewModelScope.launch {
            try {
                fetchMovie(movieId)
                    .let {
                        _uiState.value = Success(
                            movie = it,
                        )
                    }
            } catch (exception: Exception) {
                Timber
                    .tag(this.javaClass.name)
                    .e(exception)

                _uiState.value = Error(
                    error = exception.message.orEmpty(),
                    movieId = movieId,
                )
            }

        }
    }

    suspend fun saveFavouriteState(movie: Movie) {
        if (movie.isFavourited) {
            favouritesApi.favouriteMovie(movie.id)
        } else{
            favouritesApi.unfavouriteMovie(movie.id)
        }
    }

    private suspend fun fetchMovie(movieId: Int) =
        movieDataRepository.getMovie(
            userService.getBearerToken(),
            movieId,
            userService.getLanguage(),
        ).also { it.isFavourited = favouritesApi.isMovieFavourited(it.id) }
}

sealed class MovieDetailsUiState {
    object Loading : MovieDetailsUiState()
    data class Success(val movie: Movie) : MovieDetailsUiState()
    data class Error(val error: String, val movieId: Int) : MovieDetailsUiState()
}