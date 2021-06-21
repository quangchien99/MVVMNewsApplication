package com.example.newsapplication.data

import com.example.newsapplication.api.NewsApi
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val newsArticleDatabase: NewsArticleDatabase
) {
    private val newsArticleDao = newsArticleDatabase.getNewsArticleDao()

    suspend fun getBreakingNews(): List<NewsArticle> {
        val response = newsApi.getBreakingNews()
        val serverBreakingNewsArticles = response.articles
        return serverBreakingNewsArticles.map { serverBreakingNewsArticle ->
            NewsArticle(
                title = serverBreakingNewsArticle.title,
                url = serverBreakingNewsArticle.url,
                thumbnailUrl = serverBreakingNewsArticle.urlToImage,
                isBookmarked = false
            )
        }
    }
}