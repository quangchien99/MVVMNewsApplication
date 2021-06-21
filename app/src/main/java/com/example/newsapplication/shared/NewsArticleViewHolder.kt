package com.example.newsapplication.shared

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.R
import com.example.newsapplication.data.NewsArticle
import com.example.newsapplication.databinding.ItemNewsArticalBinding

class NewsArticleViewHolder(
    private val binding: ItemNewsArticalBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(article: NewsArticle) {
        binding.apply {
            Glide.with(itemView)
                .load(article.thumbnailUrl)
                .error(R.drawable.ic_error)
                .into(imgThumbnail)

            tvTitle.text = article.title ?: ""

            imgBookmark.setImageResource(
                when {
                    article.isBookmarked -> R.drawable.ic_bookmark_selected
                    else -> R.drawable.ic_bookmark_unselected
                }
            )
        }
    }
}