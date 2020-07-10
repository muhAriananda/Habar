package id.aria.habar.data.network.serivce

import id.aria.habar.domain.ArticleResponse
import id.aria.habar.utils.Const.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/top-headlines")
    suspend fun getArticle(
        @Query("country") countryCode: String = "id",
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<ArticleResponse>

    @GET("v2/everything")
    suspend fun getSearchArticle(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<ArticleResponse>
}