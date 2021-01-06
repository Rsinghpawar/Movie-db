package com.rahul.moviedb.di

import android.content.Context
import androidx.room.Room
import com.rahul.moviedb.data.database.MovieDatabase
import com.rahul.moviedb.utils.Constants.MOVIES_TABLE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        MovieDatabase::class.java,
        MOVIES_TABLE
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: MovieDatabase) = database.movieDao()
}