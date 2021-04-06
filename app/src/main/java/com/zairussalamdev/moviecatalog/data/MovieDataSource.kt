package com.zairussalamdev.moviecatalog.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.zairussalamdev.moviecatalog.data.source.local.entity.DetailEntity
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity

interface MovieDataSource {
    suspend fun getMovieList(): List<MovieEntity>

    suspend fun getTvShowList(): List<MovieEntity>

    fun getMovieDetail(movieId: Int): LiveData<DetailEntity>

    fun getTvShowDetail(tvShowId: Int): LiveData<DetailEntity>

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>>

    fun getFavoriteTvShows(): LiveData<PagedList<MovieEntity>>

    fun addFavoriteMovie(movie: MovieEntity)

    fun removeFavoriteMovie(movie: MovieEntity)

    fun checkMovieIsFavourite(id: Int) : LiveData<Boolean>
}