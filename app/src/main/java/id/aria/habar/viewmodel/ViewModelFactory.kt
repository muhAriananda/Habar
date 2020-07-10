package id.aria.habar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.aria.habar.data.network.repository.ArticleRepository

class ViewModelFactory(
    private val articleRepository: ArticleRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            return ArticleViewModel(articleRepository) as T
        }

        throw IllegalArgumentException("Unknown Class name")
    }
}