package com.mazabin.tmdbclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mazabin.tmdbclient.ui.screens.InTheatersScreen
import com.mazabin.tmdbclient.ui.theme.TMDBClientTheme
import com.mazabin.tmdbclient.viewmodels.InTheatersViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val inTheatersViewModel: InTheatersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TMDBClientTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    InTheatersScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InTheatersPreview() {
    TMDBClientTheme {
        InTheatersScreen()
    }
}