package com.ragnlabs.movies.api

import com.ragnlabs.movies.models.MovieResponse
import com.ragnlabs.movies.util.LocalData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = LocalData.API_KEY,
        @Query("page") page: Int = 1,
    ): Response<MovieResponse>

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String = LocalData.API_KEY,
        @Query("page") page: Int = 1,
    ): Response<MovieResponse>

    @GET("/3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("api_key") apiKey: String = LocalData.API_KEY,
        @Query("page") page: Int = 1,
    ): Response<MovieResponse>

    /**
     * Sample: https://api.themoviedb.org/3/search/movie?api_key=c701d68c016d5c91e50186be4b037841&language=en-US&query=ven&page=1&include_adult=false
     */
    @GET("/3/search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String = LocalData.API_KEY,
        @Query("query") searchQuery: String,
        @Query("page") page: Int = 1
    ): Response<MovieResponse>
}
