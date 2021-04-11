package com.zairussalamdev.moviecatalog.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.local.entity.DetailEntity
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity
import com.zairussalamdev.moviecatalog.utils.MovieType
import kotlinx.coroutines.launch

class DetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    var movie = MutableLiveData<MovieEntity>()

    fun getMovieDetail(type: Int, contentId: Int): LiveData<DetailEntity> {
        val result = MutableLiveData<DetailEntity>()
        viewModelScope.launch {
            val favoriteUsers = when (type) {
                MovieType.TYPE_MOVIE -> movieRepository.getMovieDetail(contentId)
                else -> movieRepository.getTvShowDetail(contentId)
            }
            result.postValue(favoriteUsers)
        }
        return result
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