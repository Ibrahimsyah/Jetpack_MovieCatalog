package com.zairussalamdev.moviecatalog.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zairussalamdev.moviecatalog.data.source.local.entity.MovieEntity

const val DB_NAME = "MOVIE_CATALOG_DB.db"

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieRoomDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var instance: MovieRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): MovieRoomDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    MovieRoomDatabase::class.java, DB_NAME
                ).build()
            }
        }

    }
}