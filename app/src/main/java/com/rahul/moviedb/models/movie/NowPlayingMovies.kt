package com.rahul.moviedb.models.movie


import com.google.gson.annotations.SerializedName

data class NowPlayingMovies(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)