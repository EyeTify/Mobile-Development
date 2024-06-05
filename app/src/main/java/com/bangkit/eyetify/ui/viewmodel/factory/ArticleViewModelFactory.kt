package com.bangkit.eyetify.ui.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bangkit.eyetify.data.injection.ArticleInjection
import com.bangkit.eyetify.data.repository.ArticleRepository
import com.bangkit.eyetify.ui.viewmodel.model.ArticleViewModel

class ArticleViewModelFactory private constructor(private val repository: ArticleRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            return ArticleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ArticleViewModelFactory? = null
        fun getInstance(): ArticleViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ArticleViewModelFactory(ArticleInjection.provideRepository())
            }.also { instance = it }
    }
}