package id.aria.habar.domain

data class ArticleResponse(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)