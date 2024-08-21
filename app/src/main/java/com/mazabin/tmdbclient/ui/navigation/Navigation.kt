package com.mazabin.tmdbclient.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mazabin.tmdbclient.ui.screens.intheaters.InTheatersScreen
import com.mazabin.tmdbclient.ui.screens.movie.MovieDetailsScreen

@Composable
fun Navigation(
    navController: NavHostController,
) {
    NavHost(navController, startDestination = Screen.InTheaters.route) {
        composable(Screen.InTheaters.route) { InTheatersScreen(navController = navController) }
        composable(Screen.MovieDetails.route) {
            val movieId = remember { it.arguments?.getString("movieId") }
            MovieDetailsScreen(movieId = movieId?.toInt() ?: 0) }
    }
}

sealed class Screen(val route: String) {
    data object InTheaters : Screen("inTheaters")
    data object MovieDetails : Screen("movieDetails/{movieId}") {
        fun createRoute(movieId: Int) = "movieDetails/$movieId"
    }
}