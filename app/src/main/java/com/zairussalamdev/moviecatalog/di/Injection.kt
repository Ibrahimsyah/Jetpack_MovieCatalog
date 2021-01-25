package com.zairussalamdev.moviecatalog.di

import android.content.Context
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.remote.RemoteDataSource
import com.zairussalamdev.moviecatalog.services.MovieDbInterface
import com.zairussalamdev.moviecatalog.services.MovieDbService

object Injection {
    fun provideRepository(context: Context): MovieRepository {
        val retrofit = MovieDbService.getInstance().create(MovieDbInterface::class.java)
        val remoteRepository = RemoteDataSource.getInstance(retrofit)
        return MovieRepository.getInstance(remoteRepository)
    }
}