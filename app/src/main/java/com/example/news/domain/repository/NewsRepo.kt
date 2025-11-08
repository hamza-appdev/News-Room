package com.example.news.domain.repository

import com.example.news.data.dto.Article
import com.example.news.data.dto.NewsResponse
import com.example.news.util.Resultt

interface NewsRepo {
    suspend fun getTopHeadline(
        category: String
    ): Resultt<List<Article>>

    suspend fun Searchfornews(
       query: String
    ): Resultt<List<Article>>

}