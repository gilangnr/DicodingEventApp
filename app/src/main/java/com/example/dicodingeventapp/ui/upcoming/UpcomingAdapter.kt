package com.example.dicodingeventapp.ui.upcoming

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingeventapp.R
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.databinding.ItemUpcomingBinding

class UpcomingAdapter: ListAdapter<ListEventsItem, UpcomingAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding: ItemUpcomingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.txtNameUpcoming.text = event.name
            binding.txtCategoryUpcoming.text = event.category
            binding.txtOwnerUpcoming.text = binding.root.context.getString(R.string.owner, event.ownerName)
            binding.txtQuotaUpcoming.text = binding.root.context.getString(R.string.quota, event.quota.toString())
            Glide.with(binding.imgUpcoming.context)
                .load(event.imageLogo)
                .into(binding.imgUpcoming)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingAdapter.MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ListEventsItem,
                newItem: ListEventsItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}