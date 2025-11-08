package com.example.news.data.dto

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)