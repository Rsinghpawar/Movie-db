package com.rahul.moviedb.data.network

import com.rahul.moviedb.models.movie.NowPlayingMovies
import com.rahul.moviedb.utils.Constants.API_KEY_
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesNowApi {


    @GET("now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey : String = API_KEY_,
        @Query("page") page : Int = 1
    ) : Response<NowPlayingMovies>
}