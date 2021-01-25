package com.zairussalamdev.moviecatalog.data

import androidx.lifecycle.LiveData
import com.zairussalamdev.moviecatalog.data.source.local.entity.DetailEntity
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity

interface MovieDataSource {
    fun getMovieList(): LiveData<List<MovieEntity>>

    fun getTvShowList(): LiveData<List<MovieEntity>>

    fun getMovieDetail(movieId: Int): LiveData<DetailEntity>

    fun getTvShowDetail(tvShowId: Int): LiveData<DetailEntity>
}