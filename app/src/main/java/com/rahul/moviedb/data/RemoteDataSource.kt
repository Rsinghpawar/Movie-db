package com.rahul.moviedb.data

import com.rahul.moviedb.data.network.MoviesNowApi
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val moviesNowApi: MoviesNowApi
) {
    suspend fun getNowPlayingMovies(page : Int) = moviesNowApi.getNowPlayingMovies(page = page)
}