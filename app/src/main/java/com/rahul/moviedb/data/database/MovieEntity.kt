package com.rahul.moviedb.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rahul.moviedb.models.movie.NowPlayingMovies
import com.rahul.moviedb.utils.Constants.MOVIES_TABLE


@Entity(tableName = MOVIES_TABLE)
class MovieEntity(
    var movies: NowPlayingMovies
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}