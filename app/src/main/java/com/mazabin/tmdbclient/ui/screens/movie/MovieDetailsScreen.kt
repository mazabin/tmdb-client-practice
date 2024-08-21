package com.mazabin.tmdbclient.ui.screens.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.mazabin.tmdbclient.R
import com.mazabin.tmdbclient.model.Movie
import com.mazabin.tmdbclient.ui.screens.LoadingScreen
import com.mazabin.tmdbclient.viewmodels.MovieDetailsUiState
import com.mazabin.tmdbclient.viewmodels.MovieDetailsViewModel

private const val BASE_URL = "https://image.tmdb.org/t/p/w500"

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    movieId: Int,
) {
    val movieDetailsViewModel = hiltViewModel<MovieDetailsViewModel>()
    val uiState = movieDetailsViewModel.uiStateFlow.collectAsState()

    when (uiState.value) {
        is MovieDetailsUiState.Loading -> {
            LoadingScreen()
        }
        is MovieDetailsUiState.Success -> MovieDetailsComponent((uiState.value as MovieDetailsUiState.Success).movie)
        is MovieDetailsUiState.Error -> {
            val errorState = uiState.value as MovieDetailsUiState.Error
            MovieDetailsErrorScreen(error = errorState.error, movieId = errorState.movieId)
        }
    }

    LaunchedEffect(Unit) {
        movieDetailsViewModel.fetchData(movieId)
    }
}

@Composable
fun MovieDetailsComponent(movie: Movie) {
    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        AsyncImage(
            model = BASE_URL + movie.backdropPath,
            contentDescription = "Background for ${movie.title}",
            modifier = Modifier.fillMaxWidth(),
        )
        if (movie.adult) {
            Icon(ImageVector.vectorResource(R.drawable.ic_18), "+18", modifier = Modifier.align(Alignment.TopStart))
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(32.dp)
                ) {
                    AsyncImage(
                        model = BASE_URL + movie.backdropPath,
                        contentDescription = "Poster for ${movie.title}",
                        modifier = Modifier,
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(4f)
                        .fillMaxSize()
                ) {
                    Text(movie.title)
                    Text(movie.originalTitle)

                    LazyRow(
                        modifier = Modifier.padding(8.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        itemsIndexed(movie.genres) { index, genre ->
                            Text(genre)
                        }
                    }
                    Text("Average rating: ${movie.voteAverage} out of ${movie.voteCount} votes")
                }
            }
            Row {
                Column {
                    Text(movie.overview)
                    Text("Release date: ${movie.releaseDate}")
                    LazyRow {
                        item {
                            Text("Production companies: ")
                        }
                        itemsIndexed(movie.productionCompanies) { index, company ->
                            Text(company)
                        }
                    }
                    Text("Original language: ${movie.originalLanguage}")
                    LazyRow {
                        item {
                            Text("Origin country: ")
                        }
                        itemsIndexed(movie.originCountry) { index, originCountry ->
                            Text(originCountry)
                        }
                    }
                }
            }
        }
    }

}
