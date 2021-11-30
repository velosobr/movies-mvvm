package com.ragnlabs.movies.models

sealed class MoviesResult<T> {
    class Success<T>(val movies: List<T>) : MoviesResult<T>()
    class ApiError(val statusCode: Int) : MoviesResult<Movie>()
    object ServerError : MoviesResult<Movie>()
}
