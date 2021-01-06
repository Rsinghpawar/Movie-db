package com.rahul.moviedb.utils

sealed class NetworkResult<T>(
    var data : T? = null,
    var message : String?=null
){
    class Success<T>(data: T) : NetworkResult<T>(data)
    class Error<T>(message: String?) : NetworkResult<T>(message = message)
    class Loading<T> : NetworkResult<T>()
}