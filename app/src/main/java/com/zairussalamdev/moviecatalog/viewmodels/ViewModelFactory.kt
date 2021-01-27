package com.zairussalamdev.moviecatalog.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.di.Injection
import com.zairussalamdev.moviecatalog.ui.detail.DetailViewModel
import com.zairussalamdev.moviecatalog.ui.favorite_detail.FavoriteDetailViewModel
import com.zairussalamdev.moviecatalog.ui.favorite_movie.FavoriteMovieViewModel
import com.zairussalamdev.moviecatalog.ui.favorite_tv_show.FavoriteTvViewModel
import com.zairussalamdev.moviecatalog.ui.movies.MovieViewModel
import com.zairussalamdev.moviecatalog.ui.tvshows.TvShowsViewModel

class ViewModelFactory private constructor(private val movieRepository: MovieRepository) :
    ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(movieRepository) as T
            }
            modelClass.isAssignableFrom(TvShowsViewModel::class.java) -> {
                TvShowsViewModel(movieRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(movieRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteTvViewModel::class.java) -> {
                FavoriteTvViewModel(movieRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                FavoriteMovieViewModel(movieRepository) as T
            }modelClass.isAssignableFrom(FavoriteDetailViewModel::class.java) -> {
                FavoriteDetailViewModel(movieRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}