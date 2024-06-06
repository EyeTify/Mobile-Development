package com.bangkit.eyetify.data.repository

import android.util.Log
import com.bangkit.eyetify.data.preference.Result
import com.bangkit.eyetify.data.response.ItemsItem
import com.bangkit.eyetify.data.response.NewsResponseItem
import com.bangkit.eyetify.data.response.SearchResponse
import com.bangkit.eyetify.data.retrofit.article.ArticleService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    suspend fun searchHealthNews(keyword: String): Result<List<ItemsItem>> =
        withContext(Dispatchers.IO) {
            try {
                val response: SearchResponse = apiService.searchHealthNews(keyword)
                val dataList = response.items?.filterNotNull() ?: emptyList()
                Result.DataSuccess(dataList)
            } catch (e: Exception) {
                Log.e("ArticleRepository", "Error searching health news", e)
                Result.DataError(e.message ?: "An unknown error occurred")
            }
        }
}
