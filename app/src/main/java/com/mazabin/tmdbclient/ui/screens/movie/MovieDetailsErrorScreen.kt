package com.mazabin.tmdbclient.ui.screens.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mazabin.tmdbclient.viewmodels.MovieDetailsViewModel

@Composable
fun MovieDetailsErrorScreen(
    modifier: Modifier = Modifier,
    error: String,
    movieId: Int,
) {
    val viewModel = hiltViewModel<MovieDetailsViewModel>()
    Box {
        Column(modifier = Modifier.align(Alignment.Center).padding(vertical = 60.dp)) {
            Text(text = "Error. Unable to fetch movie $movieId")
            Text(text = "Error message: $error")
            Text(text = "Try again later")
            Button(onClick = { viewModel.fetchData(movieId) }) {
                Text("Retry")
            }
        }
    }
}