package com.samkt.sample.util

sealed class Resource<out T>(val data:T? = null,val message:String? = null){

    class Success<T>(data: T?):Resource<T>(data)
    class Error(message: String?):Resource<Nothing>(message = message)
    object Loading:Resource<Nothing>()
}