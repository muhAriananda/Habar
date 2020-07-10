package id.aria.habar.ui.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import id.aria.habar.R
import id.aria.habar.ui.MainActivity
import id.aria.habar.utils.Const.REQUEST_SEARCH
import id.aria.habar.viewmodel.ArticleViewModel
import id.aria.habar.utils.Const.SEARCH_TIME_DELAY
import id.aria.habar.utils.adapter.ArticleAdapter
import id.aria.habar.utils.external.Resource
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {

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

        var job: Job? = null
        search_view.setIconifiedByDefault(true)
        search_view.onActionViewExpanded()

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                job?.cancel()
                job = MainScope().launch {
                    delay(SEARCH_TIME_DELAY)
                    newText?.let { viewModel.getSearchArticle(it) }
                }
                return true
            }

        })

        viewModel.searchArticleLiveData.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let {
                        articleAdapter.differ.submitList(it.articles)
                        Log.d("value Search", it.articles.toString())
                    }
                }

                is Resource.Error -> {
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                    response.message?.let {
                        Log.d("TAG", "An error : $it")
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter(REQUEST_SEARCH)
        rv_search.apply {
            adapter = articleAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun setToolbar() {
        val toolbar = view?.findViewById<Toolbar>(R.id.toolbar)
        val actionBar = (requireActivity() as AppCompatActivity)
        actionBar.setSupportActionBar(toolbar)
        actionBar.supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}