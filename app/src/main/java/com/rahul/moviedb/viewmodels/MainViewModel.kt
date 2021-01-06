package com.rahul.moviedb.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.rahul.moviedb.data.Repository
import com.rahul.moviedb.data.database.MovieEntity
import com.rahul.moviedb.models.movie.NowPlayingMovies
import com.rahul.moviedb.utils.NetworkResult
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception


class MainViewModel @ViewModelInject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    /** ROOM Database **/


    val readMovie: LiveData<List<MovieEntity>> = repository.local.readDatabase().asLiveData()

    private fun insertRecipes(movieEntity: MovieEntity) =
        viewModelScope.launch(IO) {
            repository.local.insertMovie(movieEntity)
        }


    /** RETROFIT **/
    val movieResponse: MutableLiveData<NetworkResult<NowPlayingMovies>> = MutableLiveData()

    fun getMovies(page : Int) = viewModelScope.launch {
        getMoviesSageCall(page)
    }

    private suspend fun getMoviesSageCall(page: Int) {
        movieResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getNowPlayingMovies(page)
                movieResponse.value = handleFoodRecipeResponse(response)
                val recipes = movieResponse.value!!.data
//                if (recipes != null) {
//                    offlineCacheRecipes(recipes)
//                }
            } catch (e: Exception) {

            }
        } else {
            movieResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

//    private fun offlineCacheRecipes(recipes: NowPlayingMovies?) {
//        val recipesEntity = MovieEntity(recipes)
//        insertRecipes(recipesEntity)
//    }

    private fun handleFoodRecipeResponse(response: Response<NowPlayingMovies>): NetworkResult<NowPlayingMovies>? {
        return when {
            response.message().toString().contains("timeout") -> NetworkResult.Error("Timeout")
            response.body()!!.results.isNullOrEmpty() -> NetworkResult.Error("Movies not found.")
            response.isSuccessful -> {
                val recipes = response.body()
                NetworkResult.Success(recipes!!)
            }
            else -> NetworkResult.Error(response.message())
        }
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }


    }
}