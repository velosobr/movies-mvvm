package com.ragnlabs.movies.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ragnlabs.movies.models.Movie
import com.ragnlabs.movies.models.MovieResponse
import com.ragnlabs.movies.repository.MovieRepository
import com.ragnlabs.movies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _popularMoviesList = MutableLiveData<List<Movie>>()
    val popularMoviesList: LiveData<List<Movie>>
        get() = _popularMoviesList

    private val _searchMovies = MutableLiveData<Resource<MovieResponse>>()
    val searchMovies: LiveData<Resource<MovieResponse>>
        get() = _searchMovies

    init {
        getPopularMovies()
    }

    fun getPopularMovies(page: Int = 1) = runBlocking {

        movieRepository.getPopularMovies(page).let { moviesResponse ->

            if (moviesResponse.isSuccessful) {
                _popularMoviesList.postValue(moviesResponse.body()?.results)
            } else {
                Log.d(
                    "tag",
                    "occurred error on getPopularMovies: ${moviesResponse.code()} "
                )
            }
        }
    }

    //    fun getPopularMoviesOtherWay(page: Int = 1) {
//        CoroutineScope(Dispatchers.Main).launch {
//            val movies = withContext(Dispatchers.Default) {
//                movieRepository.getPopularMovies(page)
//            }
//            _popularMoviesLiveData.value = movies.body()?.results
//        }
//    }
    fun searchMovies(searchQuery: String) = viewModelScope.launch {
        _searchMovies.postValue(Resource.Loading())
        val response = movieRepository.searchMovies(searchQuery = searchQuery)
        _searchMovies.postValue(handleSearchMoviesResponse(response))
    }

    private fun handleSearchMoviesResponse(response: Response<MovieResponse>): Resource<MovieResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }
}
