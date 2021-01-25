package com.zairussalamdev.moviecatalog.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity

class MovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    fun getAllMovies(): LiveData<List<MovieEntity>> = movieRepository.getMovieList()
}