package id.aria.habar.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.aria.habar.data.network.repository.ArticleRepository
import id.aria.habar.domain.Article
import id.aria.habar.domain.ArticleResponse
import id.aria.habar.utils.Const.CATEGORY_BUSINESS
import id.aria.habar.utils.Const.CATEGORY_ENTERTAINMENT
import id.aria.habar.utils.Const.CATEGORY_HEALTH
import id.aria.habar.utils.Const.CATEGORY_SCIENCE
import id.aria.habar.utils.Const.CATEGORY_SPORTS
import id.aria.habar.utils.Const.CATEGORY_TECHNOLOGY
import id.aria.habar.utils.Const.CATEGORY_TODAY
import id.aria.habar.utils.external.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class ArticleViewModel(
    private val repository: ArticleRepository
) : ViewModel() {

    val articleToday: MutableLiveData<Resource<ArticleResponse>> = MutableLiveData()

    val articleBusiness: MutableLiveData<Resource<ArticleResponse>> = MutableLiveData()

    val articleEntertainment: MutableLiveData<Resource<ArticleResponse>> = MutableLiveData()

    val articleHealth: MutableLiveData<Resource<ArticleResponse>> = MutableLiveData()

    val articleScience: MutableLiveData<Resource<ArticleResponse>> = MutableLiveData()

    val articleSports: MutableLiveData<Resource<ArticleResponse>> = MutableLiveData()

    val articleTechnology: MutableLiveData<Resource<ArticleResponse>> = MutableLiveData()

    val searchArticleLiveData: MutableLiveData<Resource<ArticleResponse>> = MutableLiveData()

    fun getArticles(category: String) = viewModelScope.launch {
        when (category) {
            CATEGORY_TODAY -> {
                articleToday.postValue(Resource.Loading())
                val response = repository.getArticles(category)
                articleToday.postValue(handleArticleResponse(response))
            }

            CATEGORY_BUSINESS -> {
                articleBusiness.postValue(Resource.Loading())
                val response = repository.getArticles(category)
                articleBusiness.postValue(handleArticleResponse(response))
            }

            CATEGORY_ENTERTAINMENT -> {
                articleEntertainment.postValue(Resource.Loading())
                val response = repository.getArticles(category)
                articleEntertainment.postValue(handleArticleResponse(response))
            }

            CATEGORY_HEALTH -> {
                articleHealth.postValue(Resource.Loading())
                val response = repository.getArticles(category)
                articleHealth.postValue(handleArticleResponse(response))
            }

            CATEGORY_SCIENCE -> {
                articleScience.postValue(Resource.Loading())
                val response = repository.getArticles(category)
                articleScience.postValue(handleArticleResponse(response))
            }

            CATEGORY_SPORTS -> {
                articleSports.postValue(Resource.Loading())
                val response = repository.getArticles(category)
                articleSports.postValue(handleArticleResponse(response))
            }

            CATEGORY_TECHNOLOGY -> {
                articleTechnology.postValue(Resource.Loading())
                val response = repository.getArticles(category)
                articleTechnology.postValue(handleArticleResponse(response))
            }
        }
    }

    fun getSearchArticle(query: String) = viewModelScope.launch {
        searchArticleLiveData.postValue(Resource.Loading())
        val response = repository.getSearchArticle(query)
        searchArticleLiveData.postValue(handleSearchResponse(response))
    }

    private fun handleArticleResponse(response: Response<ArticleResponse>): Resource<ArticleResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }


    private fun handleSearchResponse(response: Response<ArticleResponse>): Resource<ArticleResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.insert(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        repository.delete(article)
    }

    fun getBookmarks() = repository.getBookmark()
}