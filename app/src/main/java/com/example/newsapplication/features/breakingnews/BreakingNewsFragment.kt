package com.example.newsapplication.features.breakingnews

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentBreakingNewsBinding
import com.example.newsapplication.shared.NewsArticleAdapter
import com.example.newsapplication.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search_news.view.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    private val viewModel: BreakingNewsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentBreakingNewsBinding.bind(view)

        val newsArticleAdapter = NewsArticleAdapter()

        binding.apply {
            rcvNews.apply {
                adapter = newsArticleAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.breakingNews.collect {
                    val result = it ?: return@collect

                    swipeRefreshLayout.isRefreshing = result is Resource.Loading
                    rcvNews.isVisible = !result.data.isNullOrEmpty()
                    tvError.isVisible = result.error != null && result.data.isNullOrEmpty()
                    btnRetry.isVisible = result.error != null && result.data.isNullOrEmpty()
                    tvError.text = getString(
                        R.string.could_not_refresh,
                        result.error?.localizedMessage ?: getString(R.string.unknown_error_occurred)
                    )

                    newsArticleAdapter.submitList(result.data)
                }
            }
        }
    }
}