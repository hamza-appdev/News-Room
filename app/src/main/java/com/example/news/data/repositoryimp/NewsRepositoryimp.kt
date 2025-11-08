package com.example.news.data.repositoryimp

import com.example.news.data.dto.Article
import com.example.news.domain.repository.NewsApi
import com.example.news.domain.repository.NewsRepo
import com.example.news.util.Resultt

class NewsRepositoryImpl(
    private val newsApi: NewsApi //object
): NewsRepo{
    override suspend fun getTopHeadline(category: String): Resultt<List<Article>> {

        return try {
            val response = newsApi.getnews(category = category)
            Resultt.Success(data= response.articles)

        }catch (e:Exception){
            Resultt.Error("Failed to fetch data ${e.message}")
        }

    }

    override suspend fun Searchfornews(query: String): Resultt<List<Article>> {
        return try {
            val response = newsApi.searchfornews(query=query)
            Resultt.Success(data= response.articles)

        }catch (e:Exception){
            Resultt.Error("Failed to fetch data ${e.message}")
        }
    }

}


