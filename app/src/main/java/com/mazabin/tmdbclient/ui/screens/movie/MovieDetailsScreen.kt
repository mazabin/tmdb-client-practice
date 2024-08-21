package com.mazabin.tmdbclient.ui.screens.movie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.mazabin.tmdbclient.R
import com.mazabin.tmdbclient.model.Movie
import com.mazabin.tmdbclient.ui.screens.LoadingScreen
import com.mazabin.tmdbclient.ui.theme.Orange
import com.mazabin.tmdbclient.viewmodels.MovieDetailsUiState
import com.mazabin.tmdbclient.viewmodels.MovieDetailsViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate

private const val BASE_URL_POSTER = "https://image.tmdb.org/t/p/w500"
private const val BASE_URL_BACKDROP = "https://image.tmdb.org/t/p/w1280"

@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    movieId: Int,
) {
    val viewModel = hiltViewModel<MovieDetailsViewModel>()
    val uiState = viewModel.uiStateFlow.collectAsState()

    when (uiState.value) {
        is MovieDetailsUiState.Loading -> {
            LoadingScreen()
        }
        is MovieDetailsUiState.Success -> {
            val movie = (uiState.value as MovieDetailsUiState.Success).movie
            MovieDetailsComponent(
                movie = movie,
                favouriteAction = { viewModel.saveFavouriteState(movie) },
            )
        }
        is MovieDetailsUiState.Error -> {
            val errorState = uiState.value as MovieDetailsUiState.Error
            MovieDetailsErrorScreen(error = errorState.error, movieId = errorState.movieId)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchData(movieId)
    }
}

@Composable
fun MovieDetailsComponent(movie: Movie, favouriteAction: suspend (Movie) -> Unit = {}) {
    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        AsyncImage(
            model = BASE_URL_BACKDROP + movie.backdropPath,
            alpha = 0.3f,
            contentDescription = "Background for ${movie.title}",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxHeight()
                .matchParentSize(),
        )

        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
                .padding(vertical = 40.dp)
            ) {
                Column(
                    modifier = Modifier
                        .weight(4f)
                        .padding(start = 16.dp)

                ) {
                    Box {
                        AsyncImage(
                            placeholder = ColorPainter(Color.LightGray),
                            model = BASE_URL_POSTER + movie.posterPath,
                            contentDescription = "Poster for ${movie.title}",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxSize(),
                        )
                        if (movie.adult) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_18),
                                contentDescription = "+18",
                                tint = Color.Red,
                                modifier = Modifier
                                    .align(Alignment.TopStart))
                        }
                        val coroutineScope = rememberCoroutineScope()
                        var isChecked by remember { mutableStateOf(movie.isFavourited) }
                        IconToggleButton(
                            checked = isChecked,
                            onCheckedChange = {
                                isChecked = !isChecked
                                movie.isFavourited = !movie.isFavourited
                                coroutineScope.launch {
                                    favouriteAction.invoke(movie)
                                }
                            },
                            colors = iconColors(),
                            modifier = Modifier.align(Alignment.TopEnd)
                        ) {
                            Icon(Icons.Sharp.Star, "Favourite button")
                        }

                    }
                }
                Column(
                    modifier = Modifier
                        .weight(5f)
                        .padding(start = 16.dp)
                ) {
                    Text(text = movie.title, fontWeight = ExtraBold, fontSize = 26.sp, modifier = Modifier.padding(bottom = 8.dp))
                    Text(text = movie.originalTitle, fontStyle = FontStyle.Italic)

                    LazyRow(
                        modifier = Modifier.padding(vertical = 8.dp),
                    ) {
                        itemsIndexed(movie.genres) { index, genre ->
                            Box(Modifier.padding(end = 4.dp)) {
                                Box(
                                    Modifier
                                        .background(Color.LightGray)
                                        .padding(vertical = 2.dp, horizontal = 4.dp)
                                ) {
                                    Text(
                                        text = genre,
                                        fontWeight = SemiBold,
                                    )
                                }
                            }
                        }
                    }
                    Text("Average rating:\n${movie.voteAverage} out of ${movie.voteCount} votes")
                }
            }
            Row(
                modifier = Modifier
                    .weight(6f)
                    .padding(horizontal = 16.dp)
            ) {
                Column {
                    Text(
                        text = movie.overview,
                        fontWeight = SemiBold,
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        modifier = Modifier.padding(bottom = 16.dp),
                    )

                    Text(
                        text ="Release date: ${movie.releaseDate}",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                    Text(
                        text = "Production companies: ",
                        fontSize = 12.sp,
                        fontWeight = SemiBold,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                    LazyRow {
                        itemsIndexed(movie.productionCompanies) { index, company ->
                            Text(
                                text = "$company, ",
                                fontSize = 12.sp,
                                modifier = Modifier.padding(bottom = 8.dp),
                            )
                        }
                    }
                    Text(
                        text = "Original language: ${movie.originalLanguage}",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                    Text(
                        text = "Origin countries: ",
                        fontSize = 12.sp,
                        fontWeight = SemiBold,
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                    LazyRow {
                        itemsIndexed(movie.originCountry) { index, originCountry ->
                            Text(
                                text = "$originCountry, ",
                                fontSize = 12.sp,
                                modifier = Modifier.padding(bottom = 8.dp),
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun iconColors() =
    IconToggleButtonColors(
        contentColor = DarkGray,
        containerColor = White,
        checkedContentColor = Orange,
        checkedContainerColor = White,
        disabledContentColor = Gray,
        disabledContainerColor = DarkGray,
    )

@Preview(showBackground = true)
@Composable
fun MovieDetailsComponentPreview() {
    MovieDetailsComponent(
        movie = Movie(
                id = 123,
                title = "The Shawshank Redemption",
                originalTitle = "The Shawshank Redemption",
                originalLanguage = "en",
                overview = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency.",
                backdropPath = "/kGzFbGhp99zva6oZODW5atUtnqi.jpg",
                posterPath = "/q6y0Go1tsGEsmtFryDOJo3dEmqu.jpg",
                adult = true,
                releaseDate = LocalDate.of(1994, 9, 23),
                popularity = 85.399,
                voteAverage = 8.7,
                voteCount = 22842,
                genres = listOf("Drama", "Crime"),
                originCountry = listOf("United States"),
                runtime = 142,
                productionCompanies = listOf("Castle Rock Entertainment", "Columbia Pictures"),
                isFavourited = true
            )
        )
}
