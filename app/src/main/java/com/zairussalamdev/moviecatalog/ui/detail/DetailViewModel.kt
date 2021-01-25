package com.zairussalamdev.moviecatalog.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.local.entity.DetailEntity

class DetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getMovieDetail(type: Int, contentId: Int): LiveData<DetailEntity> {
        return when (type) {
            DetailActivity.TYPE_MOVIES -> {
                movieRepository.getMovieDetail(contentId)
            }
            else -> {
                movieRepository.getTvShowDetail(contentId)
            }
        }
    }
}