package com.mazabin.tmdbclient.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mazabin.tmdbclient.model.InTheaters
import com.mazabin.tmdbclient.viewmodels.InTheatersUiState
import com.mazabin.tmdbclient.viewmodels.InTheatersViewModel

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(140.dp)
                .align(Alignment.Center)
        )
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Loading...",
        )
    }
}

@Composable
fun MoviesList(inTheaters: InTheaters) {
    val viewModel = viewModel<InTheatersViewModel>()
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(items = inTheaters.movies) { movie ->
            MovieCard(movie = movie, favouriteAction = { viewModel.saveFavouriteState(movie) })
        }
    }
}

@Composable
fun ErrorScreen() {
    val viewModel = viewModel<InTheatersViewModel>()
    Box {
        Column(modifier = Modifier.align(Alignment.Center).padding(vertical = 60.dp)) {
            Text(text = "Error")
            Text(text = "Try again later")
            Button(onClick = { viewModel.fetchData() }) {
                Text("Retry")
            }
        }
    }
}

@Composable
fun InTheatersScreen(
    modifier: Modifier = Modifier,
) {
    val inTheatersViewModel = viewModel<InTheatersViewModel>()
    val uiState = inTheatersViewModel.uiStateFlow.collectAsState()

    when (uiState.value) {
        is InTheatersUiState.Loading -> LoadingScreen()
        is InTheatersUiState.Success -> MoviesList((uiState.value as InTheatersUiState.Success).inTheaters)
        is InTheatersUiState.Error -> ErrorScreen()
    }
}

@Preview
@Composable
fun ErrorScreenPreview() {
    ErrorScreen()
}

@Preview
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}