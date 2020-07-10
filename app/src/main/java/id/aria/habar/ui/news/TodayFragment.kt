package id.aria.habar.ui.news

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.aria.habar.R
import id.aria.habar.ui.MainActivity
import id.aria.habar.utils.Const.CATEGORY_TODAY
import id.aria.habar.utils.Const.REQUEST_ARTICLE
import id.aria.habar.utils.adapter.ArticleAdapter
import id.aria.habar.utils.external.Resource
import id.aria.habar.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class TodayFragment : Fragment(R.layout.fragment_article) {

    lateinit var viewModel: ArticleViewModel
    lateinit var articleAdapter: ArticleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()

        viewModel.getArticles(CATEGORY_TODAY)
        viewModel.articleToday.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        showProgressbar(false)
                        articleAdapter.differ.submitList(it.articles)
                    }
                }

                is Resource.Error -> {
                    showProgressbar(false)
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    showProgressbar(true)
                }
            }

        })
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter(REQUEST_ARTICLE)
        rv_articles.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showProgressbar(state: Boolean) {
        if (state) {
            rv_articles.visibility = View.INVISIBLE
            progressBar.visibility = View.VISIBLE
        } else {
            rv_articles.visibility = View.VISIBLE
            progressBar.visibility = View.INVISIBLE
        }
    }
}