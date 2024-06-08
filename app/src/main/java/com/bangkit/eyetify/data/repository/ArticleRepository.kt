package com.bangkit.eyetify.data.repository

import com.bangkit.eyetify.data.preference.Result
import com.bangkit.eyetify.data.response.NewsResponseItem
import com.bangkit.eyetify.data.retrofit.article.ArticleService
import retrofit2.HttpException

class ArticleRepository(
    private val apiService: ArticleService
) {

    suspend fun getHealthNews(): Result<List<NewsResponseItem>> {
        return try {
            val response = apiService.getHealthNews()
            Result.DataSuccess(response)
        } catch (e: HttpException) {
            if (e.code() == 404) {
                Result.DataSuccess(emptyList())
            } else {
                Result.DataError(e.message ?: "An unknown error occurred")
            }
        } catch (e: Exception) {
            Result.DataError(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun searchHealthNews(title: String): Result<List<NewsResponseItem>> {
        return try {
            val response = apiService.searchHealthNews(title)
            Result.DataSuccess(response)
        } catch (e: HttpException) {
            if (e.code() == 404) {
                Result.DataSuccess(emptyList())
            } else {
                Result.DataError(e.message ?: "An unknown error occurred")
            }
        } catch (e: Exception) {
            Result.DataError(e.message ?: "An unknown error occurred")
        }
    }
}