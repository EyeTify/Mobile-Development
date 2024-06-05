package com.bangkit.eyetify.data.retrofit.article

import com.bangkit.eyetify.data.response.NewsResponseItem
import retrofit2.http.GET

interface ArticleService {
    @GET("health-news")
    suspend fun getHealthNews(): List<NewsResponseItem>
}