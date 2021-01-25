package com.zairussalamdev.moviecatalog.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieDbService {
    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"

        @Volatile
        private var retrofit: Retrofit? = null
        fun getInstance(): Retrofit {
            return retrofit ?: synchronized(this) {
                retrofit ?: Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
        }


    }
}