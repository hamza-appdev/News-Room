package com.example.news.util

import com.example.news.data.dto.Article

//sealed class Resultt<out T> {
//    data class Success<out T>(val data:T): Resultt<T>()
//    data class Error(val message: String): Resultt<Nothing>()
//}

sealed class Resultt<out T>(val data: T?=null, val message: String?=null){
    class Success<T>(data:T?): Resultt<T>(data=data)
    class Error<T>(message: String?): Resultt<T>(message=message)
}