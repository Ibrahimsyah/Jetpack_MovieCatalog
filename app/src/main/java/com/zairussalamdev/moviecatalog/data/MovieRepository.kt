package com.zairussalamdev.moviecatalog.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.zairussalamdev.moviecatalog.data.source.local.LocalDataSource
import com.zairussalamdev.moviecatalog.data.source.local.entity.DetailEntity
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity
import com.zairussalamdev.moviecatalog.data.source.remote.RemoteDataSource
import com.zairussalamdev.moviecatalog.data.source.remote.response.Movie
import com.zairussalamdev.moviecatalog.data.source.remote.response.MovieDetailResponse
import com.zairussalamdev.moviecatalog.data.source.remote.response.TvShow
import com.zairussalamdev.moviecatalog.data.source.remote.response.TvShowDetailResponse
import com.zairussalamdev.moviecatalog.utils.AppExecutors

class MovieRepository private constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) :
    MovieDataSource {

    companion object {
        @Volatile
        var instance: MovieRepository? = null

        fun getInstance(
            remoteDataSource: RemoteDataSource,
            localDataSource: LocalDataSource,
            appExecutors: AppExecutors
        ): MovieRepository {
            return instance ?: synchronized(this) {
                instance ?: MovieRepository(remoteDataSource, localDataSource, appExecutors)
            }
        }
    }

    override fun getMovieList(): LiveData<List<MovieEntity>> {
        val moviesResult = MutableLiveData<List<MovieEntity>>()
        remoteDataSource.getMovieList(object : RemoteDataSource.MovieListCallback {
            override fun onMovieListLoaded(movies: List<Movie>?) {
                val movieList = ArrayList<MovieEntity>()
                movies?.forEach { movie ->
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
                moviesResult.postValue(movieList)
            }
        })
        return moviesResult
    }

    override fun getTvShowList(): LiveData<List<MovieEntity>> {
        val tvShowsResult = MutableLiveData<List<MovieEntity>>()
        remoteDataSource.getTvShowList(object : RemoteDataSource.TvShowListCallback {
            override fun onTvShowListLoaded(tvShows: List<TvShow>?) {
                val tvShowList = ArrayList<MovieEntity>()
                tvShows?.forEach { tvShow ->
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
                tvShowsResult.postValue(tvShowList)
            }
        })

        return tvShowsResult
    }

    override fun getMovieDetail(movieId: Int): LiveData<DetailEntity> {
        val movieDetailResult = MutableLiveData<DetailEntity>()
        remoteDataSource.getMovieDetail(movieId, object : RemoteDataSource.MovieDetailCallback {
            override fun onMovieDetailLoaded(movieDetail: MovieDetailResponse?) {
                val detail = DetailEntity(
                    id = movieDetail?.id,
                    title = movieDetail?.title,
                    overview = movieDetail?.overview,
                    posterPath = movieDetail?.posterPath,
                    backdropPath = movieDetail?.backdropPath,
                    releaseDate = movieDetail?.releaseDate,
                    voteCount = movieDetail?.voteCount,
                    voteAverage = movieDetail?.voteAverage,
                    tagLine = movieDetail?.tagLine,
                    homepage = movieDetail?.homepage,
                    status = movieDetail?.status
                )
                movieDetailResult.postValue(detail)
            }
        })
        return movieDetailResult
    }

    override fun getTvShowDetail(tvShowId: Int): LiveData<DetailEntity> {
        val tvShowDetailResult = MutableLiveData<DetailEntity>()
        remoteDataSource.getTvShowDetail(tvShowId, object : RemoteDataSource.TvShowDetailCallback {
            override fun onTvShowDetailLoaded(tvShowDetail: TvShowDetailResponse?) {
                val detail = DetailEntity(
                    id = tvShowDetail?.id,
                    title = tvShowDetail?.title,
                    overview = tvShowDetail?.overview,
                    posterPath = tvShowDetail?.posterPath,
                    backdropPath = tvShowDetail?.backdropPath,
                    releaseDate = tvShowDetail?.releaseDate,
                    voteCount = tvShowDetail?.voteCount,
                    voteAverage = tvShowDetail?.voteAverage,
                    tagLine = tvShowDetail?.tagLine,
                    homepage = tvShowDetail?.homepage,
                    status = tvShowDetail?.status
                )
                tvShowDetailResult.postValue(detail)
            }
        })
        return tvShowDetailResult
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
        appExecutors.diskIO().execute{
            localDataSource.insertFavoriteMovie(movie)
        }
    }

    override fun removeFavoriteMovie(movie: MovieEntity) {
        appExecutors.diskIO().execute{
            localDataSource.deleteFavoriteMovie(movie)
        }
    }

    override fun checkMovieIsFavourite(id: Int): LiveData<Boolean> {
        return localDataSource.checkMovieIsFavourite(id)
    }
}