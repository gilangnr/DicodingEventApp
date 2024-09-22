package com.example.dicodingeventapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.dicodingeventapp.R
import com.example.dicodingeventapp.data.response.Event
import com.example.dicodingeventapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        val eventId = intent.getStringExtra("EVENT_ID")

        if (eventId != null) {
            viewModel.getDetail(eventId)
        }

        viewModel.event.observe(this) {
            setDetailData(it)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    private fun setDetailData(event: Event) {
        binding.nameDetail.text = event.name
        binding.categoryDetail.text = event.category
        binding.descriptionDetail.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.ownerDetail.text = binding.root.context.getString(R.string.owner, event.ownerName)
        binding.locationDetail.text = binding.root.context.getString(R.string.location, event.cityName)
        binding.waktuDetail.text = binding.root.context.getString(R.string.begin, event.beginTime)
        binding.quotaDetail.text = binding.root.context.getString(R.string.quota_detail, event.quota - event.registrants)
        Glide.with(binding.imgDetail.context)
            .load(event.mediaCover)
            .into(binding.imgDetail)
    }

    private fun showLoading(isLoading: Boolean) {
       binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}