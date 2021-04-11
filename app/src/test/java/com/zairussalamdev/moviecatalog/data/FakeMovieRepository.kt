package com.zairussalamdev.moviecatalog.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.zairussalamdev.moviecatalog.data.source.local.LocalDataSource
import com.zairussalamdev.moviecatalog.data.source.local.entity.DetailEntity
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity
import com.zairussalamdev.moviecatalog.data.source.remote.RemoteDataSource
import com.zairussalamdev.moviecatalog.utils.AppExecutors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FakeMovieRepository(
    private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    MovieDataSource {

    override suspend fun getMovieList(): List<MovieEntity> {
        val result = withContext(Dispatchers.IO) { remoteDataSource.getMovieList() }
        val movieList = ArrayList<MovieEntity>()
        result.movies.forEach { movie ->
            movieList.add(
                MovieEntity(
                    overview = movie.overview,
                    title = movie.title,
                    posterPath = movie.posterPath,
                    voteAverage = movie.voteAverage,
                    id = movie.id,
                )
            )
        }
        return movieList
    }

    override suspend fun getTvShowList(): List<MovieEntity> {
        val result = withContext(Dispatchers.IO) { remoteDataSource.getTvShowList() }
        val tvShowList = ArrayList<MovieEntity>()
        result.tvShows.forEach { tvShow ->
            tvShowList.add(
                MovieEntity(
                    overview = tvShow.overview,
                    title = tvShow.name,
                    posterPath = tvShow.posterPath,
                    voteAverage = tvShow.voteAverage,
                    id = tvShow.id,
                )
            )
        }
        return tvShowList
    }

    override suspend fun getMovieDetail(movieId: Int): DetailEntity {
        val movieDetail = remoteDataSource.getMovieDetail(movieId)
        return DetailEntity(
            id = movieDetail.id,
            title = movieDetail.title,
            overview = movieDetail.overview,
            posterPath = movieDetail.posterPath,
            backdropPath = movieDetail.backdropPath,
            releaseDate = movieDetail.releaseDate,
            voteCount = movieDetail.voteCount,
            voteAverage = movieDetail.voteAverage,
            tagLine = movieDetail.tagLine,
            homepage = movieDetail.homepage,
            status = movieDetail.status
        )
    }

    override suspend fun getTvShowDetail(tvShowId: Int): DetailEntity {
        val tvShowDetail = remoteDataSource.getTvShowDetail(tvShowId)
        return DetailEntity(
            id = tvShowDetail.id,
            title = tvShowDetail.title,
            overview = tvShowDetail.overview,
            posterPath = tvShowDetail.posterPath,
            backdropPath = tvShowDetail.backdropPath,
            releaseDate = tvShowDetail.releaseDate,
            voteCount = tvShowDetail.voteCount,
            voteAverage = tvShowDetail.voteAverage,
            tagLine = tvShowDetail.tagLine,
            homepage = tvShowDetail.homepage,
            status = tvShowDetail.status
        )
    }

    override fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavouriteMovies(), config).build()
    }

    override fun getFavoriteTvShows(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteTvShows(), config).build()
    }

    override fun addFavoriteMovie(movie: MovieEntity) {
        appExecutors.diskIO().execute {
            localDataSource.insertFavoriteMovie(movie)
        }
    }

    override fun removeFavoriteMovie(movie: MovieEntity) {
        appExecutors.diskIO().execute {
            localDataSource.deleteFavoriteMovie(movie)
        }
    }

    override fun checkMovieIsFavourite(id: Int): LiveData<Boolean> {
        return localDataSource.checkMovieIsFavourite(id)
    }
}