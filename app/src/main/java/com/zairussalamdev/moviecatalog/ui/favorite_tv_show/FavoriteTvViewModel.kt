package com.zairussalamdev.moviecatalog.ui.favorite_tv_show

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity

class FavoriteTvViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    fun getAllTvShows(): LiveData<PagedList<MovieEntity>> {
        return  movieRepository.getFavoriteTvShows()
    }
}