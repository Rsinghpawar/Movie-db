package com.rahul.moviedb.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rahul.moviedb.utils.Constants.API_KEY
import com.rahul.moviedb.utils.Constants.QUERY_ADD_RECIPE_INFORMATION
import com.rahul.moviedb.utils.Constants.QUERY_API_KEY
import com.rahul.moviedb.utils.Constants.QUERY_DIET
import com.rahul.moviedb.utils.Constants.QUERY_FILL_INGREDIENTS
import com.rahul.moviedb.utils.Constants.QUERY_NUMBER
import com.rahul.moviedb.utils.Constants.QUERY_TYPE


class MovieViewModel(application: Application) : AndroidViewModel(application) {

     fun applyQueries() : HashMap<String, String> {
        val queries : HashMap<String , String> = HashMap()
         queries[QUERY_NUMBER] = "50"
         queries[QUERY_API_KEY] = API_KEY
         queries[QUERY_TYPE] = "snack"
         queries[QUERY_DIET] = "vegan"
         queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
         queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries

    }
}