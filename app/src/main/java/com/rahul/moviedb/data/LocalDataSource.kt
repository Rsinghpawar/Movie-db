package com.rahul.moviedb.data

import com.rahul.moviedb.data.database.MovieDao
import com.rahul.moviedb.data.database.MovieEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val dao: MovieDao
) {

    suspend fun insertMovie(movie: MovieEntity) {
        dao.insert(movie)
    }

    fun readDatabase(): Flow<List<MovieEntity>> {
        return dao.readMovies()
    }
}