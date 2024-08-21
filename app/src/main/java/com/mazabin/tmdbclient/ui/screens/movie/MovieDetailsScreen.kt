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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.mazabin.tmdbclient.viewmodels.MovieDetailsUiState
import com.mazabin.tmdbclient.viewmodels.MovieDetailsViewModel
import java.time.LocalDate

private const val BASE_URL_POSTER = "https://image.tmdb.org/t/p/w500"
private const val BASE_URL_BACKDROP = "https://image.tmdb.org/t/p/w1280"


@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
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
            model = BASE_URL_BACKDROP + movie.backdropPath,
            alpha = 0.3f,
            contentDescription = "Background for ${movie.title}",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .fillMaxHeight()
                .matchParentSize(),
        )
        if (movie.adult) {
            Icon(ImageVector.vectorResource(R.drawable.ic_18), "+18", modifier = Modifier.align(Alignment.TopStart))
        }
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
                    Box(modifier = Modifier.background(Color.Blue)) {
                        AsyncImage(
                            model = BASE_URL_POSTER + movie.posterPath,
                            contentDescription = "Poster for ${movie.title}",
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier.fillMaxSize(),
                        )
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
                adult = false,
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
