# Movies App
Android App using [The MovieDB API](https://developers.themoviedb.org/3)

# Description
Primarily the app is showing a list of Popular, Top Rated and Upcoming movies, which could be slider to the left to see more movies.

- Utilizes a simple MVVM pattern, similar to [this archtecture presented by google developers](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png) but without the use of Fragments and database, just for saving time purpose and apply for the job in time.
- Implemented Unit Tests for the Viewmodel.


## Libraries Used
Coroutine, LiveData, Retrofit2, Coil, Dagger-Hilt, mockk & JUnit.

## Features Done
- Show list of: Popular, TopRated and Upcoming movies.

## Features in progress
  - Search movies

## improvements
 - remove runblocking in MoviesViewModel and change for another best practice way of handling blocking threads.

## TO DO
  - Save favorites movies in local database when longpress the moviecard
  - Show theses favorites movies in a new screen
  - Show movie details in a new screen
  
