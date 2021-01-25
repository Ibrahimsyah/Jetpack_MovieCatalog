package com.zairussalamdev.moviecatalog.services

import com.zairussalamdev.moviecatalog.BuildConfig
import com.zairussalamdev.moviecatalog.data.source.remote.response.MovieDetailResponse
import com.zairussalamdev.moviecatalog.data.source.remote.response.MovieResponse
import com.zairussalamdev.moviecatalog.data.source.remote.response.TvShowDetailResponse
import com.zairussalamdev.moviecatalog.data.source.remote.response.TvShowResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

const val apiKey: String = BuildConfig.TMDB_API_KEY

interface MovieDbInterface {

    @GET("movie/now_playing?api_key=$apiKey")
    fun getNowPlayingMovies(): Call<MovieResponse>

    @GET("tv/popular?api_key=$apiKey")
    fun getPopularTvShows(): Call<TvShowResponse>

    @GET("movie/{movieId}?api_key=$apiKey")
    fun getMovieDetail(@Path("movieId") movieId: Int): Call<MovieDetailResponse>

    @GET("tv/{tvId}?api_key=$apiKey")
    fun getTvShowDetail(@Path("tvId") tvId: Int): Call<TvShowDetailResponse>
}

