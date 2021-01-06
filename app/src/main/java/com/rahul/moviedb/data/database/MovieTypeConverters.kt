package com.rahul.moviedb.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rahul.moviedb.models.movie.NowPlayingMovies

class MovieTypeConverters {

    var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(movies: NowPlayingMovies): String {
        return gson.toJson(movies)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String): NowPlayingMovies {
        val listType = object : TypeToken<NowPlayingMovies>() {}.type
        return gson.fromJson(data, listType)
    }

}