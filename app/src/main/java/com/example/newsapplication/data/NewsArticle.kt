package com.example.newsapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_articles")
data class NewsArticle(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val url: String,
    val title: String?,
    val thumbnailUrl: String?,
    val isBookmarked: Boolean,
    val updateAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "breaking_news")
data class BreakingNews(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val articleUrl: String
)