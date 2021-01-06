package com.rahul.moviedb.bindingadpaters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.rahul.moviedb.data.database.MovieEntity
import com.rahul.moviedb.models.movie.NowPlayingMovies
import com.rahul.moviedb.utils.NetworkResult

class MoviesNowFragmentBinding {

    companion object {

        @JvmStatic
        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: NetworkResult<NowPlayingMovies>?,
            database: List<MovieEntity>?
        ) {
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else if (apiResponse is NetworkResult.Loading || apiResponse is NetworkResult.Success) {
                imageView.visibility = View.INVISIBLE
            }

        }

        @JvmStatic
        @BindingAdapter("readApiResponse2", "readDatabase2", requireAll = true)
        fun errorTextViewVisibility(
            textView: TextView,
            apiResponse: NetworkResult<NowPlayingMovies>?,
            database: List<MovieEntity>?
        ) {
            if (apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            } else if (apiResponse is NetworkResult.Loading || apiResponse is NetworkResult.Success) {
                textView.visibility = View.INVISIBLE
            }

        }
    }
}