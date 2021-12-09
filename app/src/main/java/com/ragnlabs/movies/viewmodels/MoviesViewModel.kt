package com.ragnlabs.movies.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragnlabs.movies.models.Movie
import com.ragnlabs.movies.models.MoviesResult
import com.ragnlabs.movies.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val popularMoviesList: MutableLiveData<List<Movie>> = MutableLiveData()

    val topRatedMoviesList: MutableLiveData<List<Movie>> = MutableLiveData()

    val upcomingMoviesList: MutableLiveData<List<Movie>> = MutableLiveData()

    val searchMovies: MutableLiveData<List<Movie>> = MutableLiveData()

//    init {
//        // getPopularMovies()
//        getTopRatedMovies()
// //        getUpcomingMovies()
//    }

    fun getPopularMovies(page: Int = 1) = viewModelScope.launch {

        movieRepository.getPopularMovies(page).let { moviesResponse ->

            if (moviesResponse.isSuccessful) {
                popularMoviesList.postValue(moviesResponse.body()?.results)
            } else {
                Log.d(
                    "tag",
                    "occurred error on getPopularMovies: ${moviesResponse.code()} "
                )
            }
        }
    }

    fun getTopRatedMovies(page: Int = 1) = viewModelScope.launch {
        val response = movieRepository.getTopRatedMovies(page)
        topRatedMoviesList.postValue(response)
    }

    fun getUpcomingMovies(page: Int = 1) = viewModelScope.launch {
        when (val result = movieRepository.getUpcomingMovies(page)) {
            is MoviesResult.Success -> {
                upcomingMoviesList.value = result.movies
            }
            is MoviesResult.ApiError -> {
                if (result.statusCode == 401) {
                    Log.d(
                        "error-tag",
                        "occurred API error on getUpcomingMovies: ${result.statusCode} "
                    )
                } else {
                    Log.d(
                        "error-tag",
                        "occurred error on getUpcomingMovies: ${result.statusCode} "
                    )
                }
            }
            is MoviesResult.ServerError -> {
                Log.d(
                    "error-tag",
                    "occurred a Server error on getUpcomingMovies: Erro catastrófico"
                )
            }
        }
    }

    fun searchMovies(searchQuery: String) = viewModelScope.launch {
        val response = movieRepository.searchMovies(searchQuery = searchQuery)
        searchMovies.postValue(response)
    }
}
