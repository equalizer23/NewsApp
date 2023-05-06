package com.example.newsapp.models

data class TopNewsResponse(
    val status: String? = null,
    val totalResults: String? = null,
    val articles: List<TopNewsArticle>? = null
)
