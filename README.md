# Simple TMDB client

1. Collection of movies currently playing in cinemas:
   https://api.themoviedb.org/3/movie/now_playing
   https://developer.themoviedb.org/reference/movie-now-playing-list
   20 results per page
2. Details of the movie after click
   https://api.themoviedb.org/3/movie/{movie_id}
   https://developer.themoviedb.org/reference/movie-details
3. Search
   https://api.themoviedb.org/3/search/movie
   https://developer.themoviedb.org/reference/search-movie
4. Make movie favouritable, which is marked by a star
   Persistency handled with DataStore, but Room would be preferred with bigger data to handle 
5. minSdk = 26

## What works:
Movies currently in theaters screen, with pagination to get more movies
Movie details
Favourites system with persistence

## Missing
More tests
Search
Proper architecture split into separated modules
UI Tests

## Stack
Java 19
Kotlin 2.0.0
Gradle 4.1.3
AGP 8.7.0-alpha03
Jetpack Navigation, Compose
Coroutines
Hilt
Protobuf, DataStore
Retrofit
Coil
Mockk, Junit, AssertJ
Timber