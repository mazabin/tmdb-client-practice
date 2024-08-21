package com.mazabin.tmdbclient.ui.screens.intheaters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mazabin.tmdbclient.model.InTheaters
import com.mazabin.tmdbclient.ui.screens.LoadingScreen
import com.mazabin.tmdbclient.viewmodels.InTheatersUiState
import com.mazabin.tmdbclient.viewmodels.InTheatersViewModel
import kotlinx.coroutines.launch

@Composable
fun MoviesList(inTheaters: InTheaters, navController: NavController) {
    val viewModel = hiltViewModel<InTheatersViewModel>()
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
        state = gridState,
    ) {
        items(items = inTheaters.movies) { movie ->
            MovieCard(
                movie = movie,
                favouriteAction = { viewModel.saveFavouriteState(movie) },
                navController = navController
            )
        }
    }
    val isSecondToLastRowVisible = remember {
        derivedStateOf {
            gridState.isSecondToLastRowVisible()
        }
    }

    val coroutineScope = rememberCoroutineScope()
    if (isSecondToLastRowVisible.value)
        SideEffect {
           coroutineScope.launch {
               viewModel.fetchNextPage()
           }
        }
}

@Composable
fun InTheatersScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val inTheatersViewModel = hiltViewModel<InTheatersViewModel>()
    val uiState = inTheatersViewModel.uiStateFlow.collectAsState()

    when (uiState.value) {
        is InTheatersUiState.Loading -> LoadingScreen()
        is InTheatersUiState.Success -> MoviesList((uiState.value as InTheatersUiState.Success).inTheaters, navController)
        is InTheatersUiState.Error -> ErrorScreenInTheaters()
    }
}

@Preview
@Composable
fun ErrorScreenPreview() {
    ErrorScreenInTheaters()
}

fun LazyGridState.isSecondToLastRowVisible() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == (layoutInfo.totalItemsCount - 3)