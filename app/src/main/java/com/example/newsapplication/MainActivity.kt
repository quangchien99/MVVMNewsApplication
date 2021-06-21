package com.example.newsapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.newsapplication.databinding.ActivityMainBinding
import com.example.newsapplication.features.bookmarks.BookmarksFragment
import com.example.newsapplication.features.breakingnews.BreakingNewsFragment
import com.example.newsapplication.features.searchnews.SearchNewsFragments
import dagger.hilt.android.AndroidEntryPoint
import java.lang.IllegalArgumentException

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var searchNewsFragment: SearchNewsFragments
    private lateinit var breakingNewsFragment: BreakingNewsFragment
    private lateinit var bookmarksFragment: BookmarksFragment

    private val fragments: Array<Fragment>
        get() = arrayOf(
            breakingNewsFragment,
            searchNewsFragment,
            bookmarksFragment
        )

    private var selectedIndex = 0

    private val selectedFragment get() = fragments[selectedIndex]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            breakingNewsFragment = BreakingNewsFragment()
            searchNewsFragment = SearchNewsFragments()
            bookmarksFragment = BookmarksFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, breakingNewsFragment, TAG_BREAKING_NEWS_FRAGMENT)
                .add(R.id.fragmentContainer, searchNewsFragment, TAG_SEARCH_NEWS_FRAGMENT)
                .add(R.id.fragmentContainer, bookmarksFragment, TAG_BOOKMARKS_FRAGMENT)
                .commit()
        } else {
            breakingNewsFragment =
                supportFragmentManager.findFragmentByTag(TAG_BREAKING_NEWS_FRAGMENT) as BreakingNewsFragment
            searchNewsFragment =
                supportFragmentManager.findFragmentByTag(TAG_SEARCH_NEWS_FRAGMENT) as SearchNewsFragments
            bookmarksFragment =
                supportFragmentManager.findFragmentByTag(TAG_BOOKMARKS_FRAGMENT) as BookmarksFragment

            selectedIndex = savedInstanceState.getInt(KEY_SELECTED_INDEX, 0)
        }
        selectFragment(selectedFragment)

        binding.bottomNav.setOnNavigationItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_breaking -> breakingNewsFragment
                R.id.nav_search -> searchNewsFragment
                R.id.nav_bookmarks -> bookmarksFragment
                else -> throw IllegalArgumentException("Unexpected Error - itemID: ${item.itemId}")
            }
            selectFragment(fragment)
            true
        }
    }

    private fun selectFragment(selectedFragment: Fragment) {
        var transaction = supportFragmentManager.beginTransaction()
        fragments.forEachIndexed { index, fragment ->
            if (selectedFragment == fragment) {
                transaction = transaction.attach(fragment)
                selectedIndex = index
            } else {
                transaction = transaction.detach(fragment)
            }
        }
        transaction.commit()

        title = when (selectedFragment) {
            is BreakingNewsFragment -> getString(R.string.title_breaking_news)
            is SearchNewsFragments -> getString(R.string.title_search_news)
            is BookmarksFragment -> getString(R.string.title_bookmarks)
            else -> ""
        }
    }

}

private const val TAG_BREAKING_NEWS_FRAGMENT = "TAG_BREAKING_NEWS_FRAGMENT"
private const val TAG_SEARCH_NEWS_FRAGMENT = "TAG_SEARCH_NEWS_FRAGMENT"
private const val TAG_BOOKMARKS_FRAGMENT = "TAG_BOOKMARKS_FRAGMENT"
private const val KEY_SELECTED_INDEX = "KEY_SELECTED_INDEX"