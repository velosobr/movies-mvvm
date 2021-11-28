package com.ragnlabs.movies

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ragnlabs.movies.adapters.PopularMoviesAdapter
import com.ragnlabs.movies.adapters.TopRatedMoviesAdapter
import com.ragnlabs.movies.adapters.UpComingMoviesAdapter
import com.ragnlabs.movies.databinding.ActivityMainBinding
import com.ragnlabs.movies.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    private lateinit var topRatedMoviesAdapter: TopRatedMoviesAdapter
    private lateinit var upcomingMoviesAdapter: UpComingMoviesAdapter
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadMoviesRecyclerView()
    }

    private fun loadMoviesRecyclerView() {

        setupPopularMovies()
        setupTopRatedMovies()
        setupUpcomingMovies()
    }

    private fun setupPopularMovies() {
        popularMoviesAdapter = PopularMoviesAdapter()

        binding.popularMoviesListRecyclerView.apply {
            adapter = popularMoviesAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
        }

        viewModel.popularMoviesList.observe(this, { moviesList ->
            popularMoviesAdapter.movies = moviesList
        })
    }

    private fun setupTopRatedMovies() {
        topRatedMoviesAdapter = TopRatedMoviesAdapter()

        binding.topRatedMoviesListRecyclerView.apply {
            adapter = topRatedMoviesAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
        }

        viewModel.topRatedMoviesList.observe(this, { moviesList ->
            topRatedMoviesAdapter.movies = moviesList
        })
    }

    private fun setupUpcomingMovies() {
        upcomingMoviesAdapter = UpComingMoviesAdapter()

        binding.upcomingMoviesListRecyclerView.apply {
            adapter = upcomingMoviesAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
        }

        viewModel.upcomingMoviesList.observe(this, { moviesList ->
            upcomingMoviesAdapter.movies = moviesList
        })
    }
}
