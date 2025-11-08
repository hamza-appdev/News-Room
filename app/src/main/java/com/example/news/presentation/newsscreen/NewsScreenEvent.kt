package com.example.news.presentation.newsscreen

import com.example.news.data.dto.Article

sealed class NewsScreenEvent {
    data class OnNewCardClicked(var article: Article): NewsScreenEvent() //on user action we get values
    data class OnCategoryChanged(var category: String): NewsScreenEvent()
    data class OnScreenQuerychanged(var searchQuery: String): NewsScreenEvent()
    object OnSearchIconClicked: NewsScreenEvent()
    object OnCloseIconClicked: NewsScreenEvent()
}