package com.example.news.presentation.newsscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.news.data.dto.Article
import com.example.news.domain.repository.NewsRepo
import com.example.news.util.Resultt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewmodels @Inject constructor(
    private val newsRepo: NewsRepo
): ViewModel() {

    //for states
    var state by mutableStateOf(NewsScreenstate())

    private var searchjob: Job? =null




    fun onEvent(event: NewsScreenEvent){
        when(event) {
            is NewsScreenEvent.OnCategoryChanged -> {
                state=state.copy(category = event.category)
                getNewsArticle(category = state.category)
            }

            is NewsScreenEvent.OnNewCardClicked -> {
                state=state.copy(selectedArticle = event.article)
            }

            is NewsScreenEvent.OnScreenQuerychanged -> {
                state= state.copy(searchQuery = event.searchQuery)
                searchjob?.cancel()
                searchjob = viewModelScope.launch {
                    delay(1000L)
                    searchForNews(query = state.searchQuery )
                }


            }

            NewsScreenEvent.OnSearchIconClicked -> {
                state=state.copy(
                    isSearchBarVisible = true,
                    article = emptyList()
                    )
            }

            NewsScreenEvent.OnCloseIconClicked -> {
                state=state.copy(
                    isSearchBarVisible = false,
                    searchQuery = "",
                    article = emptyList()
                    )
                getNewsArticle(category = state.category)
            }
        }
    }



    private fun getNewsArticle(category: String){
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = newsRepo.getTopHeadline(category = category)
            when(result){
                is Resultt.Error -> {
                    state=state.copy(
                        article = emptyList(),
                        isLoading =false,
                        error = result.message
                    )
                                    }
                is Resultt.Success -> {
                   state=state.copy(
                       article = result.data ?:emptyList(),
                       isLoading =false,
                       error = null
                   )
                }
            }

        }
    }

    private fun searchForNews(query: String){
        if (query.isEmpty()){
            return
        }
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = newsRepo.Searchfornews(query=query)
            when(result){
                is Resultt.Error -> {
                    state=state.copy(
                        article = emptyList(),
                        isLoading =false,
                        error = result.message
                    )
                }
                is Resultt.Success -> {
                    state=state.copy(
                        article = result.data ?:emptyList(),
                        isLoading =false,
                        error = null
                    )
                }
            }

        }
    }
}














