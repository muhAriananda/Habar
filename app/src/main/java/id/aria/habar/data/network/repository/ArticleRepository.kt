package id.aria.habar.data.network.repository

import id.aria.habar.data.db.ArticleDatabase
import id.aria.habar.data.network.serivce.RetrofitInstance
import id.aria.habar.domain.Article

class ArticleRepository(
    private val db: ArticleDatabase
) {
    suspend fun getArticles(category: String) =
        RetrofitInstance.api.getArticle(category= category)

    suspend fun getSearchArticle(query: String) =
        RetrofitInstance.api.getSearchArticle(query)

    suspend fun insert(article: Article)= db.getArticleDao().insert(article)

    suspend fun delete(article: Article) = db.getArticleDao().delete(article)

    fun getBookmark() = db.getArticleDao().getAllArticle()
}