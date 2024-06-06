package com.bangkit.eyetify.data.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.eyetify.data.response.ItemsItem
import com.bangkit.eyetify.databinding.ItemNewsBinding

class SearchArticleAdapter : RecyclerView.Adapter<SearchArticleAdapter.ArticleViewHolder>() {

    private val articles = ArrayList<ItemsItem>()

    class ArticleViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(article: ItemsItem) {
            binding.tvNewsTitle.text = article.title
            binding.tvNewsDescription.text = article.url

            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(article.url)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArticleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(articles[position])
    }

    override fun getItemCount(): Int = articles.size

    fun submitList(newArticles: List<ItemsItem>) {
        articles.clear()
        articles.addAll(newArticles)
        notifyDataSetChanged()
    }
}
