package com.zairussalamdev.moviecatalog.ui.favorite_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity

class FavoriteMovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    fun getAllMovies() : LiveData<PagedList<MovieEntity>> {
        return movieRepository.getFavoriteMovies()
    }
}