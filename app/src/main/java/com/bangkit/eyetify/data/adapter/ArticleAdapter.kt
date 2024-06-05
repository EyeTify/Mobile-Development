package com.bangkit.eyetify.data.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.eyetify.data.response.NewsResponseItem
import com.bangkit.eyetify.data.utils.DateFormatter
import com.bangkit.eyetify.databinding.ItemNewsBinding
import com.bumptech.glide.Glide

class ArticleAdapter : ListAdapter<NewsResponseItem, ArticleAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class MyViewHolder(private var binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: NewsResponseItem){
            binding.tvNewsTitle.text = currentItem.title
            binding.tvNewsDescription.text = currentItem.description
            Glide.with(binding.root)
                .load(currentItem.urlToImage)
                .into(binding.ivNews)
            binding.tvItemPublishedDate.text = DateFormatter.formatDate(currentItem.publishedAt)

            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(currentItem.url)
                itemView.context.startActivity(intent)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsResponseItem>() {
            override fun areItemsTheSame(oldItem: NewsResponseItem, newItem: NewsResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: NewsResponseItem, newItem: NewsResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}