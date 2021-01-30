package com.zairussalamdev.moviecatalog.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity
import com.zairussalamdev.moviecatalog.utils.MovieType

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieEntity)

    @Delete
    fun delete(movie: MovieEntity)

    @Update
    fun update(movie: MovieEntity)

    @RawQuery(observedEntities = [MovieEntity::class])
    fun checkMovieIsFavourite(query: SupportSQLiteQuery): LiveData<Boolean>

    @Query("SELECT * FROM MOVIES WHERE movie_type = ${MovieType.TYPE_MOVIE} ")
    fun getAllFavoriteMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movies WHERE movie_type = ${MovieType.TYPE_TV_SHOW}")
    fun getAllFavoriteTvShows(): DataSource.Factory<Int, MovieEntity>
}

