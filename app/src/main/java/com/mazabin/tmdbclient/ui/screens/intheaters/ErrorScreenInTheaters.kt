package com.mazabin.tmdbclient.ui.screens.intheaters

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
import com.mazabin.tmdbclient.viewmodels.InTheatersViewModel

@Composable
fun ErrorScreenInTheaters() {
    val viewModel = hiltViewModel<InTheatersViewModel>()
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