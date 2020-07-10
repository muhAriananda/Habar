package id.aria.habar.utils.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import id.aria.habar.R
import id.aria.habar.domain.Article
import id.aria.habar.utils.Const.ITEM_FIRST
import id.aria.habar.utils.Const.ITEM_SECOND
import id.aria.habar.utils.Const.REQUEST_ARTICLE
import id.aria.habar.utils.Const.REQUEST_BOOKMARKS
import id.aria.habar.utils.Const.REQUEST_SEARCH
import kotlinx.android.synthetic.main.item_list.view.*

class ArticleAdapter(private val requestCode: Int) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.ViewHolder {
        when(requestCode) {
            REQUEST_ARTICLE -> {
                if (viewType == ITEM_FIRST) {
                    return ViewHolder(
                        LayoutInflater.from(parent.context).inflate(R.layout.item_list_first, parent, false)
                    )
                }
                return ViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
                )
            }

            REQUEST_SEARCH -> {
                return ViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
                )
            }

            REQUEST_BOOKMARKS -> {
                return ViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
                )
            }
        }

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        )

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ViewHolder, position: Int) {
        val article = differ.currentList[position]
        val bundle = Bundle().apply {
            putSerializable("article", article)
        }

        holder.itemView.apply {
            Picasso.get().load(article.urlToImage).into(img_article)
            tv_title.text = article.title

            if (article.content != null) {
                tv_content.text = article.content
            } else {
                tv_content.text = article.source.name
            }

            setOnClickListener {
                when (requestCode) {
                    REQUEST_ARTICLE -> {
                        findNavController().navigate(
                            R.id.action_articleFragment_to_detailArticleFragment2,
                            bundle
                        )
                    }

                    REQUEST_BOOKMARKS -> {
                        findNavController().navigate(
                            R.id.action_bookmarkFragment_to_detailArticleFragment,
                            bundle
                        )
                    }

                    REQUEST_SEARCH -> {
                        findNavController().navigate(
                            R.id.action_searchFragment_to_detailArticleFragment,
                            bundle
                        )
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return ITEM_FIRST
        }
        return ITEM_SECOND
    }
}