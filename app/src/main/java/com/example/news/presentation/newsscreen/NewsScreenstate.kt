package com.example.news.presentation.newsscreen

import com.example.news.data.dto.Article

data class NewsScreenstate(
    val isLoading: Boolean = true,
    val article: List<Article> = emptyList(),
    val error : String? =null,
    val isSearchBarVisible: Boolean = false,
    val selectedArticle: Article? = null,
    val category: String = "General",
    val searchQuery: String = ""

)