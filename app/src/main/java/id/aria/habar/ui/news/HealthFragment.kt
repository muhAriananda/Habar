package id.aria.habar.ui.news

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.aria.habar.R
import id.aria.habar.ui.MainActivity
import id.aria.habar.utils.Const
import id.aria.habar.utils.Const.CATEGORY_HEALTH
import id.aria.habar.utils.Const.REQUEST_ARTICLE
import id.aria.habar.utils.adapter.ArticleAdapter
import id.aria.habar.utils.external.Resource
import id.aria.habar.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.fragment_article.*

class HealthFragment: Fragment(R.layout.fragment_health) {

    private lateinit var viewmodel: ArticleViewModel
    private lateinit var articleAdapter: ArticleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ((activity) as MainActivity).viewModel
        setupRecyclerView()

        viewmodel.getArticles(CATEGORY_HEALTH)
        viewmodel.articleHealth.observe(viewLifecycleOwner, Observer { response ->
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