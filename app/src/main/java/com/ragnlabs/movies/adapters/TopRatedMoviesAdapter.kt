package com.ragnlabs.movies.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ragnlabs.movies.databinding.MovieItemBinding
import com.ragnlabs.movies.models.Movie
import com.ragnlabs.movies.util.LocalData

class TopRatedMoviesAdapter() :
    RecyclerView.Adapter<TopRatedMoviesAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallBack = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return newItem == oldItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    var movies: List<Movie>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentMovie = movies[position]

        holder.binding.apply {
            movieTitleTextView.text = currentMovie.title

            moviePosterImageView.load(LocalData.IMAGE_BASE_URL + currentMovie.poster_path) {
                crossfade(true)
                crossfade(1000)
            }
        }
    }

    override fun getItemCount() = movies.size
}
