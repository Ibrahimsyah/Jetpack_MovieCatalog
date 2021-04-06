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