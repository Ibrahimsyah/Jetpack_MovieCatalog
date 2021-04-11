package com.zairussalamdev.moviecatalog.data.source.remote

import com.zairussalamdev.moviecatalog.data.source.remote.response.*
import com.zairussalamdev.moviecatalog.services.MovieDbInterface
import com.zairussalamdev.moviecatalog.utils.EspressoIdlingResource

class RemoteDataSource private constructor(private val apiService: MovieDbInterface) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(apiService: MovieDbInterface): RemoteDataSource {
            return instance ?: synchronized(this) {
                instance ?: RemoteDataSource(apiService)
            }
        }
    }

    suspend fun getMovieList(): MovieResponse {
        EspressoIdlingResource.increment()
        val result = apiService.getNowPlayingMovies()
        EspressoIdlingResource.decrement()
        return result
    }

    suspend fun getTvShowList(): TvShowResponse {
        EspressoIdlingResource.increment()
        val result = apiService.getPopularTvShows()
        EspressoIdlingResource.decrement()
        return result
    }

    suspend fun getMovieDetail(movieId: Int): MovieDetailResponse {
        EspressoIdlingResource.increment()
        val result = apiService.getMovieDetail(movieId)
        EspressoIdlingResource.decrement()
        return result
    }

    suspend fun getTvShowDetail(tvShowId: Int): TvShowDetailResponse {
        EspressoIdlingResource.increment()
        val result = apiService.getTvShowDetail(tvShowId)
        EspressoIdlingResource.decrement()
        return result
    }

    interface MovieListCallback{
        fun onMovieListLoaded(movies : List<Movie>?)
    }

    interface MovieDetailCallback{
        fun onMovieDetailLoaded(movieDetail: MovieDetailResponse?)
    }

    interface TvShowListCallback{
        fun onTvShowListLoaded(tvShows : List<TvShow>?)
    }

    interface TvShowDetailCallback{
        fun onTvShowDetailLoaded(tvShowDetail: TvShowDetailResponse?)
    }
}