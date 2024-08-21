package com.mazabin.tmdbclient.ui.screens.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun MovieDetailsScreen(
    navController: NavController,
) {

    Box(Modifier.fillMaxSize()) {
        Text(text = "Movie Details", modifier = Modifier.align(Alignment.Center))
    }
}