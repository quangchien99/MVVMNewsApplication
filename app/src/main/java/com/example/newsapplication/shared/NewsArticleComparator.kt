package com.example.newsapplication.shared

import androidx.recyclerview.widget.DiffUtil
import com.example.newsapplication.data.NewsArticle

class NewsArticleComparator : DiffUtil.ItemCallback<NewsArticle>() {
    override fun areItemsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: NewsArticle, newItem: NewsArticle): Boolean =
        oldItem == newItem

}