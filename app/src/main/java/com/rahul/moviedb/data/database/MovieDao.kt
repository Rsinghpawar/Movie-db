package com.rahul.moviedb.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rahul.moviedb.data.database.MovieEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    @Query("Select * from movies_table Order by id ASC")
    fun readMovies() : Flow<List<MovieEntity>>

}