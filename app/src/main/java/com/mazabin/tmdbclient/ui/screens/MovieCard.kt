package com.mazabin.tmdbclient.ui.screens

import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mazabin.tmdbclient.model.Movie
import com.mazabin.tmdbclient.ui.theme.Orange
import kotlinx.coroutines.launch

private const val BASE_URL = "https://image.tmdb.org/t/p/w500"

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movie: Movie,
    favouriteAction: suspend (Movie) -> Unit,
)  {
    Card {
        Column {
            Box(modifier = Modifier.fillMaxWidth()) {
                val coroutineScope = rememberCoroutineScope()
                AsyncImage(
                    model = BASE_URL + movie.posterPath,
                    contentDescription = "Poster for ${movie.title}",
                    modifier = Modifier.fillMaxWidth(),
                )
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
            Text(
                text = movie.title,
                fontWeight = Bold,
                color = Black,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                minLines = 2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Text(text = "Rating: ", fontWeight = SemiBold, color = Black)
                Text(
                    text = DecimalFormat("#.0").format(movie.voteAverage).toString(),
                    fontWeight = ExtraBold,
                    color = Black
                )
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