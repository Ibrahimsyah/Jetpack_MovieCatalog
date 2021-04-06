package com.zairussalamdev.moviecatalog.ui.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity
import kotlinx.coroutines.launch

class MovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {
    private val movieList = MutableLiveData<List<MovieEntity>>()

    fun getAllMovies(): LiveData<List<MovieEntity>> {
        viewModelScope.launch {
            val movies = movieRepository.getMovieList()
            Log.d("hehehe", movies.toString())
            movieList.postValue(movies)
        }
        return movieList
    }
}