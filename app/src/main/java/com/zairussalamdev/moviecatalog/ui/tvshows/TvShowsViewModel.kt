package com.zairussalamdev.moviecatalog.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity
import kotlinx.coroutines.launch

class TvShowsViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val tvShowList = MutableLiveData<List<MovieEntity>>()

    fun getAllTvShows(): LiveData<List<MovieEntity>> {
        viewModelScope.launch {
            val result = movieRepository.getTvShowList()
            tvShowList.postValue(result)
        }
        return tvShowList
    }
}