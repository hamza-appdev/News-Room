package com.example.news.domain.repository

import com.example.news.data.dto.Constant
import com.example.news.data.dto.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    //https://newsapi.org/v2/top-headlines?country=us&apiKey=""'
    @GET("top-headlines")
    suspend fun getnews(
        @Query("category") category: String,
        @Query("country") country: String="us",
        @Query("apiKey") apiKey: String = Constant.apikey
    ): NewsResponse

    @GET("everything")
    suspend fun searchfornews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = Constant.apikey
    ): NewsResponse
}