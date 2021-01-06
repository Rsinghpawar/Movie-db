package com.rahul.moviedb.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [MovieEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(MovieTypeConverters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao() : MovieDao
}