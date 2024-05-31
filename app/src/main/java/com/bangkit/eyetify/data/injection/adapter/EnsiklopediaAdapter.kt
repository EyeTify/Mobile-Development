package com.bangkit.eyetify.data.injection.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.eyetify.data.model.EnsiklopediaModel
import com.bangkit.eyetify.databinding.ItemEnsiklopediaBinding
import com.bangkit.eyetify.ui.fragment.article.DetailEnsiklopediaActivity

class EnsiklopediaAdapter(private val listEnsiklopedia: ArrayList<EnsiklopediaModel>)
    : RecyclerView.Adapter<EnsiklopediaAdapter.EnsiklopediaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EnsiklopediaViewHolder {
        val binding = ItemEnsiklopediaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EnsiklopediaViewHolder(binding)
    }

    override fun getItemCount(): Int = listEnsiklopedia.size

    override fun onBindViewHolder(holder: EnsiklopediaViewHolder, position: Int) {
        val (name, photo, description) = listEnsiklopedia[position]
        holder.binding.tvTitledesease.text = name
        holder.binding.imgEyedesease.setImageResource(photo)
        holder.binding.tvDesciptiondesease.text = description

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailEnsiklopediaActivity::class.java)
            intentDetail.putExtra("key_ensiklopedia", listEnsiklopedia[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    class EnsiklopediaViewHolder(val binding: ItemEnsiklopediaBinding) : RecyclerView.ViewHolder(binding.root)
}