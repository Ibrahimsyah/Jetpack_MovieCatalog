package com.zairussalamdev.moviecatalog.di

import android.content.Context
import com.zairussalamdev.moviecatalog.data.MovieRepository
import com.zairussalamdev.moviecatalog.data.source.local.LocalDataSource
import com.zairussalamdev.moviecatalog.data.source.remote.RemoteDataSource
import com.zairussalamdev.moviecatalog.database.MovieRoomDatabase
import com.zairussalamdev.moviecatalog.services.MovieDbInterface
import com.zairussalamdev.moviecatalog.services.MovieDbService
import com.zairussalamdev.moviecatalog.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): MovieRepository {
        val retrofit = MovieDbService.getInstance().create(MovieDbInterface::class.java)
        val database = MovieRoomDatabase.getDatabase(context)
        val appExecutors = AppExecutors()
        val remoteRepository = RemoteDataSource.getInstance(retrofit)
        val localRepository = LocalDataSource.getInstance(database.movieDao())
        return MovieRepository.getInstance(remoteRepository, localRepository, appExecutors)
    }
}