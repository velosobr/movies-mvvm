package com.ragnlabs.movies.repository

import com.ragnlabs.movies.api.ApiService
import com.ragnlabs.movies.models.Movie
import com.ragnlabs.movies.models.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getPopularMovies(page: Int): Response<MovieResponse> {
        return apiService.getPopularMovies(page = page)
    }

    suspend fun onViewReady() {
        getUpcomingMovies(1)
    }

    suspend fun getTopRatedMovies(page: Int): List<Movie>? {
        return apiService.getTopRatedMovies(page = page).body()?.results
    }

    suspend fun getUpcomingMovies(page: Int): List<Movie>? {
        return apiService.getUpcomingMovies(page = page).body()?.results
    }

    suspend fun searchMovies(searchQuery: String): List<Movie>? {
        return apiService.searchMovies(searchQuery = searchQuery).body()?.results
    }
}
