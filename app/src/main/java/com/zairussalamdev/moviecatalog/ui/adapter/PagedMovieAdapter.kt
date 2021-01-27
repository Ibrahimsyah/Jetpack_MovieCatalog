package com.zairussalamdev.moviecatalog.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity
import com.zairussalamdev.moviecatalog.databinding.ItemMovieBinding

class PagedMovieAdapter internal constructor() :
    PagedListAdapter<MovieEntity, PagedMovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
    private var movies = ArrayList<MovieEntity>()
    private lateinit var listener : (MovieEntity) -> Unit

    fun getSwipedData(swipedPosition: Int): MovieEntity? = getItem(swipedPosition)

    fun setMovies(movies: List<MovieEntity>?){
        movies?.let{
            this.movies.clear()
            this.movies.addAll(it)
            this.notifyDataSetChanged()
        }
    }

    fun setListener(listener : (MovieEntity) -> Unit){
        this.listener = listener
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            binding.movieTitle.text = movie.title
            binding.movieDesc.text = movie.overview
            Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
                .into(binding.moviePoster)
            itemView.setOnClickListener { listener(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) =
        holder.bind(movies[position])


    override fun getItemCount(): Int = movies.size
}