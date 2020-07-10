package id.aria.habar.ui.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import id.aria.habar.R
import id.aria.habar.domain.Article
import id.aria.habar.ui.MainActivity
import id.aria.habar.viewmodel.ArticleViewModel
import kotlinx.android.synthetic.main.fragment_detail_article.*

class DetailArticleFragment : Fragment(R.layout.fragment_detail_article) {

    private val arg: DetailArticleFragmentArgs by navArgs()

    lateinit var viewModel: ArticleViewModel
    lateinit var article: Article
    lateinit var menu: Menu

    var state = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)

        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        setToolbar()

        article = arg.article
        webView.apply {
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.userAgentString = "Android"
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_detail, menu)
        this.menu = menu
        if (article.id == null) {
            state = true
            checkBookmark()
        } else {
            state = false
            checkBookmark()
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_bookmark -> {
                if (state) {
                    viewModel.saveArticle(article)
                    Snackbar.make(detail, "Article is saved", Snackbar.LENGTH_LONG).show()
                } else {
                    viewModel.deleteArticle(article)
                    Snackbar.make(detail, "Article is delete", Snackbar.LENGTH_LONG).apply {
                        setAction("Undo") {
                            viewModel.saveArticle(article)
                            state = !state
                            checkBookmark()
                        }
                        show()
                    }
                }
                state = !state
                checkBookmark()
            }

            R.id.action_share -> {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.putExtra(Intent.EXTRA_TEXT, article.url)
                shareIntent.type = "text/plain"
                Intent.createChooser(shareIntent, "Share via")
                startActivity(shareIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkBookmark() {
        if (state) {
            menu.getItem(0).icon = context?.let { ContextCompat.getDrawable(it,R.drawable.ic_bookmark_stroke) }
        } else {
            menu.getItem(0).icon = context?.let { ContextCompat.getDrawable(it, R.drawable.ic_bookmark) }
        }
    }

    private fun setToolbar() {
        val toolbar = view?.findViewById<Toolbar>(R.id.toolbar)
        val actionBar = (requireActivity() as AppCompatActivity)
        actionBar.setSupportActionBar(toolbar)
        actionBar.supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}