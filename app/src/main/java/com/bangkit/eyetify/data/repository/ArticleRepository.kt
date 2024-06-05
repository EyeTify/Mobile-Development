package com.bangkit.eyetify.data.repository

import com.bangkit.eyetify.data.preference.Result
import com.bangkit.eyetify.data.response.NewsResponseItem
import com.bangkit.eyetify.data.retrofit.article.ArticleService

class ArticleRepository(
    private val apiService: ArticleService
) {

    suspend fun getHealthNews(): Result<List<NewsResponseItem>> {
        return try {
            val response = apiService.getHealthNews()
            Result.DataSuccess(response)
        } catch (e: Exception) {
            Result.DataError(e.message ?: "An unknown error occurred")
        }
    }
}