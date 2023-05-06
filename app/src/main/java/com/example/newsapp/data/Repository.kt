package com.example.newsapp.data

import com.example.newsapp.network.NewsManager

class Repository(val manager: NewsManager) {
    suspend fun getArticles() =
        manager.getArticles("us")

    suspend fun getArticlesByCategory(category: String) =
        manager.getArticlesByCategory(category)

    suspend fun getArticlesBySource(source: String) =
        manager.getArticlesBySource(source)

    suspend fun getArticlesBySearch(query: String) =
        manager.getSearchedArticles(query)
}