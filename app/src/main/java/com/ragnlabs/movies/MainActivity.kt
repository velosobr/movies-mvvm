package com.ragnlabs.movies

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ragnlabs.movies.adapter.MoviesAdapter
import com.ragnlabs.movies.databinding.ActivityMainBinding
import com.ragnlabs.movies.viewmodels.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var moviesAdapter: MoviesAdapter
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadMoviesRecyclerView()
    }

    private fun loadMoviesRecyclerView() {
        moviesAdapter = MoviesAdapter()

        binding.moviesListRecyclerView.apply {
            adapter = moviesAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL,
                false
            )
            setHasFixedSize(true)
        }
        viewModel.popularMoviesList.observe(this, { moviesList ->

            moviesAdapter.movies = moviesList
        })
    }
}
