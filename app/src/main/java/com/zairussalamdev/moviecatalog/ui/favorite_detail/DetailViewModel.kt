package com.zairussalamdev.moviecatalog.ui.favorite_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.local.entity.DetailEntity
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity
import com.zairussalamdev.moviecatalog.utils.MovieType

class FavoriteDetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    var movie = MutableLiveData<MovieEntity>()
    fun getMovieDetail(type: Int, contentId: Int): LiveData<DetailEntity> {
        return when (type) {
            MovieType.TYPE_MOVIE -> {
                movieRepository.getMovieDetail(contentId)
            }
            else -> {
                movieRepository.getTvShowDetail(contentId)
            }
        }
    }

    fun checkMovieIsFavourite(id: Int): LiveData<Boolean>{
        return movieRepository.checkMovieIsFavourite(id)
    }

    fun insertFavoriteMovie(movie : MovieEntity){
        movieRepository.addFavoriteMovie(movie)
    }

    fun deleteFavoriteMovie(movie: MovieEntity){
        return movieRepository.removeFavoriteMovie(movie)
    }
}