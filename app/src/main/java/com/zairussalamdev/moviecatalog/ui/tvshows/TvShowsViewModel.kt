package com.zairussalamdev.moviecatalog.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity

class TvShowsViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getAllTvShows(): LiveData<List<MovieEntity>> {
        return  movieRepository.getTvShowList()
    }
}