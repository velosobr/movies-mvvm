package com.ragnlabs.movies.repository

import com.ragnlabs.movies.api.ApiService
import com.ragnlabs.movies.models.Movie
import com.ragnlabs.movies.models.MovieBodyResponse
import com.ragnlabs.movies.models.MoviesResult
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getPopularMovies(page: Int): Response<MovieBodyResponse> {
        return apiService.getPopularMovies(page = page)
    }

    suspend fun onViewReady() {
        getUpcomingMovies(1)
    }

    suspend fun getTopRatedMovies(page: Int): List<Movie>? {
        return apiService.getTopRatedMovies(page = page).body()?.results
    }

    suspend fun getUpcomingMovies(page: Int): MoviesResult<Movie>? {
        return try {
            val movieResponse = apiService.getUpcomingMovies()
            val movies: MutableList<Movie> = mutableListOf()
            for (movieResult in movieResponse.results) {
                movies.add(movieResult)
            }
            MoviesResult.Success(movies)
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                MoviesResult.ApiError(401)
            } else {
                MoviesResult.ServerError
            }
        }
    }

    suspend fun searchMovies(searchQuery: String): List<Movie>? {
        return apiService.searchMovies(searchQuery = searchQuery).body()?.results
    }
}
