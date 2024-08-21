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
5. minSdk = 26

What works:
InTheaters screen opens, has some movie details and has persistent favouriting

Missing:
Fetching more with pagination
Navigation to movie details view
Movie details view
Tests

Cleanup: commented out things, abandoned ideas, formatting