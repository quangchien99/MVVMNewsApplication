package com.example.newsapplication.data

import androidx.room.withTransaction
import com.example.newsapplication.api.NewsApi
import com.example.newsapplication.util.Resource
import com.example.newsapplication.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsApi: NewsApi,
    private val newsArticleDatabase: NewsArticleDatabase
) {
    private val newsArticleDao = newsArticleDatabase.getNewsArticleDao()

    fun getBreakingNews(): Flow<Resource<List<NewsArticle>>> = networkBoundResource(
        query = {
            newsArticleDao.getAllBreakingNewsArticles()
        },
        fetch = {
            val response = newsApi.getBreakingNews()
            response.articles
        },
        saveFetchResult = { serverBreakingNewsArticles ->
            val breakingNewsArticles = serverBreakingNewsArticles.map { serverBreakingNewsArticle ->
                NewsArticle(
                    title = serverBreakingNewsArticle.title,
                    url = serverBreakingNewsArticle.url,
                    thumbnailUrl = serverBreakingNewsArticle.urlToImage,
                    isBookmarked = false
                )
            }

            val breakingNews = breakingNewsArticles.map { article ->
                BreakingNews(
                    articleUrl = article.url
                )
            }

            newsArticleDatabase.withTransaction {
                newsArticleDao.deleteAllBreakingNews()
                newsArticleDao.insertArticles(breakingNewsArticles)
                newsArticleDao.insertBreakingNews(breakingNews)
            }
        }
    )
}