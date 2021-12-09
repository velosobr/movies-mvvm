package com.ragnlabs.movies.models

data class MovieBodyResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)
