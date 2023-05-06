package com.example.newsapp

import android.media.AudioDescriptor
import android.media.Image

data class NewsData(
    val id: Int,
    val title: String,
    val image: Int,
    val author: String,
    val description: String,
    val date: String
)