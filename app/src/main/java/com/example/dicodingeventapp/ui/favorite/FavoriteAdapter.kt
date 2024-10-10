package com.example.dicodingeventapp.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingeventapp.data.local.entity.FavoriteEvent
import com.example.dicodingeventapp.databinding.ItemFinishedBinding

class FavoriteAdapter(private val onItemClick: (FavoriteEvent) -> Unit) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var favoriteEvent: List<FavoriteEvent> = emptyList()

    fun submitList(favoriteEvent: List<FavoriteEvent>) {
        this.favoriteEvent = favoriteEvent
        notifyDataSetChanged()
    }
    class ViewHolder(private val binding: ItemFinishedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: FavoriteEvent, onItemClick: (FavoriteEvent) -> Unit) {
            binding.txtNameFinished.text = event.name
            Glide.with(binding.imgFinished.context)
                .load(event.mediaCover)
                .into(binding.imgFinished)

            itemView.setOnClickListener{ onItemClick(event)}
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFinishedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = favoriteEvent[position]
        holder.bind(event, onItemClick)
    }

    override fun getItemCount(): Int = favoriteEvent.size
}