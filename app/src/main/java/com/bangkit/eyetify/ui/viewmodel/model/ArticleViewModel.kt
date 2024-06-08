package com.bangkit.eyetify.ui.viewmodel.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.eyetify.data.preference.Result
import com.bangkit.eyetify.data.repository.ArticleRepository
import com.bangkit.eyetify.data.response.NewsResponseItem
import kotlinx.coroutines.launch

class ArticleViewModel (private val repository: ArticleRepository) : ViewModel() {

    private val _articles = MutableLiveData<Result<List<NewsResponseItem>>>()
    val articles: LiveData<Result<List<NewsResponseItem>>> get() = _articles

    fun getAllArticles() {
        viewModelScope.launch {
            _articles.value = Result.DataLoading
            val result = repository.getHealthNews()
            _articles.value = result
        }
    }

    fun searchArticles(title: String) {
        viewModelScope.launch {
            _articles.value = Result.DataLoading
            val result = repository.searchHealthNews(title)
            _articles.value = result
        }
    }
}