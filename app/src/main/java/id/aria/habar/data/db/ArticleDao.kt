package id.aria.habar.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import id.aria.habar.domain.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Query("SELECT * FROM articles ORDER BY id DESC")
    fun getAllArticle(): LiveData<List<Article>>

    @Delete
    suspend fun delete(article: Article)
}