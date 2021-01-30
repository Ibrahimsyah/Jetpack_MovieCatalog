package com.zairussalamdev.moviecatalog.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.sqlite.db.SimpleSQLiteQuery
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity
import com.zairussalamdev.moviecatalog.database.MovieDao

class LocalDataSource private constructor(private val movieDao: MovieDao) {

    companion object {
        private var instance: LocalDataSource? = null

        fun getInstance(movieDao: MovieDao): LocalDataSource {
            return instance ?: synchronized(this) {
                instance ?: LocalDataSource(movieDao)
            }
        }
    }

    fun getFavouriteMovies(): DataSource.Factory<Int, MovieEntity> =
        movieDao.getAllFavoriteMovies()

    fun getFavoriteTvShows(): DataSource.Factory<Int, MovieEntity> =
        movieDao.getAllFavoriteTvShows()

    fun insertFavoriteMovie(movie: MovieEntity) {
        movieDao.update(movie);
        movieDao.insert(movie)
    }

    fun deleteFavoriteMovie(movie: MovieEntity) {
        movieDao.update(movie);
        movieDao.delete(movie)
    }

    fun checkMovieIsFavourite(id: Int): LiveData<Boolean> {
        val query = SimpleSQLiteQuery("select exists(select * from movies where id = $id)")
        return movieDao.checkMovieIsFavourite(query)
    }
}