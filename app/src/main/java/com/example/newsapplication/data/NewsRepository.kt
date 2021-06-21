package com.example.newsapplication.data

import com.example.newsapplication.api.NewsApi
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val newsArticleDatabase: NewsArticleDatabase
) {
    private val newsArticleDao = newsArticleDatabase.getNewsArticleDao()
}