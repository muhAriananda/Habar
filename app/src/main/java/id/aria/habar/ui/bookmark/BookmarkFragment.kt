package id.aria.habar.ui.bookmark

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.aria.habar.R
import id.aria.habar.ui.MainActivity
import id.aria.habar.utils.Const.REQUEST_BOOKMARKS
import id.aria.habar.utils.adapter.ArticleAdapter
import id.aria.habar.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class BookmarkFragment : Fragment(R.layout.fragment_bookmark) {
    lateinit var viewModel: ArticleViewModel
    lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setToolbar()
        setupRecyclerView()

        viewModel.getBookmarks().observe(viewLifecycleOwner, Observer {bookmarks ->
            articleAdapter.differ.submitList(bookmarks)
        })
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter(REQUEST_BOOKMARKS)
        rv_articles.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setToolbar() {
        val toolbar = view?.findViewById<Toolbar>(R.id.toolbar_bookmark)
        val actionBar = (requireActivity() as AppCompatActivity)
        actionBar.setSupportActionBar(toolbar)
        actionBar.supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}