package com.bangkit.eyetify.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.eyetify.data.room.ResultEntity
import com.bangkit.eyetify.databinding.ItemSavedBinding
import com.bumptech.glide.Glide

class ResultSavedAdapter(private val listResultHistory: List<ResultEntity>
): RecyclerView.Adapter<ResultSavedAdapter.ListViewHolder>() {
    class ListViewHolder(val binding: ItemSavedBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemSavedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = listResultHistory.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val resultHistory = listResultHistory[position]
        val context =holder.itemView.context

        Glide.with(context).load(resultHistory.imageUri).into(holder.binding.ivHistory)

        with(holder.binding){
            tvHistoryResult.text = resultHistory.result
            tvHistorySuggestion.text = resultHistory.suggestion
        }
    }
}