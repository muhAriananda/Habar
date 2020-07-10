package id.aria.habar.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import id.aria.habar.R
import id.aria.habar.data.db.ArticleDatabase
import id.aria.habar.data.network.repository.ArticleRepository
import id.aria.habar.viewmodel.ArticleViewModel
import id.aria.habar.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = ArticleRepository(ArticleDatabase(this))
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(ArticleViewModel::class.java)
    }
}