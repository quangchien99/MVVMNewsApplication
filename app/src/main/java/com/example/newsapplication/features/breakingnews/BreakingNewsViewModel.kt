package com.example.newsapplication.features.breakingnews

import androidx.lifecycle.ViewModel
import com.example.newsapplication.data.NewsRepository
import javax.inject.Inject

class BreakingNewsViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {
}