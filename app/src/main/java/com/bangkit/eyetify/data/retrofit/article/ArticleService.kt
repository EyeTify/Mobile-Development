package com.bangkit.eyetify.data.retrofit.article

import com.bangkit.eyetify.data.response.NewsResponseItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleService {
    @GET("health-news")
    suspend fun getHealthNews(): List<NewsResponseItem>

    @GET("search-articles")
    suspend fun searchHealthNews(
        @Query("title") title: String
    ): List<NewsResponseItem>
}