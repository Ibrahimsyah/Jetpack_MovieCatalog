package com.zairussalamdev.moviecatalog.data.source.remote

import com.zairussalamdev.moviecatalog.data.source.remote.response.*
import com.zairussalamdev.moviecatalog.services.MovieDbInterface
import com.zairussalamdev.moviecatalog.utils.EspressoIdlingResource
import retrofit2.Callback
import retrofit2.Response

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

    fun getMovieList(callback: MovieListCallback) {
        EspressoIdlingResource.increment()
        apiService.getNowPlayingMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(
                call: retrofit2.Call<MovieResponse>,
                response: Response<MovieResponse>
            ) {
                val movies = response.body()?.movies
                EspressoIdlingResource.decrement()
                return callback.onMovieListLoaded(movies)
            }

            override fun onFailure(call: retrofit2.Call<MovieResponse>, t: Throwable) {
            }
        })
    }

    fun getTvShowList(callback: TvShowListCallback) {
        EspressoIdlingResource.increment()
        apiService.getPopularTvShows().enqueue(object : Callback<TvShowResponse> {
            override fun onResponse(
                call: retrofit2.Call<TvShowResponse>,
                response: Response<TvShowResponse>
            ) {
                val tvShows = response.body()?.tvShows
                EspressoIdlingResource.decrement()
                return callback.onTvShowListLoaded(tvShows)
            }

            override fun onFailure(call: retrofit2.Call<TvShowResponse>, t: Throwable) {
            }
        })
    }

    fun getMovieDetail(movieId: Int, callback: MovieDetailCallback) {
        EspressoIdlingResource.increment()
        apiService.getMovieDetail(movieId).enqueue(object : Callback<MovieDetailResponse> {
            override fun onResponse(
                call: retrofit2.Call<MovieDetailResponse>,
                responseMovie: Response<MovieDetailResponse>
            ) {
                val movieDetail = responseMovie.body()
                EspressoIdlingResource.decrement()
                return callback.onMovieDetailLoaded(movieDetail)
            }

            override fun onFailure(call: retrofit2.Call<MovieDetailResponse>, t: Throwable) {
            }
        })
    }

    fun getTvShowDetail(tvShowId: Int, callback : TvShowDetailCallback) {
        EspressoIdlingResource.increment()
        apiService.getTvShowDetail(tvShowId).enqueue(object : Callback<TvShowDetailResponse> {
            override fun onResponse(
                call: retrofit2.Call<TvShowDetailResponse>,
                responseMovie: Response<TvShowDetailResponse>
            ) {
                val tvShowDetail = responseMovie.body()
                EspressoIdlingResource.decrement()
                return callback.onTvShowDetailLoaded(tvShowDetail)
            }

            override fun onFailure(call: retrofit2.Call<TvShowDetailResponse>, t: Throwable) {
            }
        })
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