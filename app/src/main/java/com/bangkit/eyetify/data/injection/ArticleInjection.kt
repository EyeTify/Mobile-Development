package com.bangkit.eyetify.data.injection

import com.bangkit.eyetify.data.repository.ArticleRepository
import com.bangkit.eyetify.data.retrofit.article.ArticleConfig

object ArticleInjection {
    fun provideRepository(): ArticleRepository {
        val articleApiService = ArticleConfig.getArticleConfig()
        return ArticleRepository(articleApiService)
    }
}