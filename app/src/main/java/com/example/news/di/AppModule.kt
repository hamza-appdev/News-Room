package com.example.news.di

import android.annotation.SuppressLint
import com.example.news.data.dto.Constant.Base_url
import com.example.news.data.repositoryimp.NewsRepositoryImpl
import com.example.news.domain.repository.NewsApi
import com.example.news.domain.repository.NewsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @SuppressLint("SuspiciousIndentation")
    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi{
         val retrofit =   Retrofit.Builder() //object of retrofit
                .baseUrl(Base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
               return retrofit.create(NewsApi::class.java)


    }

    @Provides
    @Singleton
    fun provideNewsRepository(newsApi: NewsApi): NewsRepo{
        return NewsRepositoryImpl(newsApi = newsApi)
    }



}