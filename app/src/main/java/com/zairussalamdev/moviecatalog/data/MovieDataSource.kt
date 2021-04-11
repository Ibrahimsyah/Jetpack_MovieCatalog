package com.zairussalamdev.moviecatalog.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.zairussalamdev.moviecatalog.data.source.local.entity.DetailEntity
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity

interface MovieDataSource {
    suspend fun getMovieList(): List<MovieEntity>

    suspend fun getTvShowList(): List<MovieEntity>

    suspend fun getMovieDetail(movieId: Int): DetailEntity

    suspend fun getTvShowDetail(tvShowId: Int): DetailEntity

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>>

    fun getFavoriteTvShows(): LiveData<PagedList<MovieEntity>>

    fun addFavoriteMovie(movie: MovieEntity)

    fun removeFavoriteMovie(movie: MovieEntity)

    fun checkMovieIsFavourite(id: Int) : LiveData<Boolean>
}