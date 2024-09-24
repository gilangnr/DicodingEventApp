package com.example.dicodingeventapp.ui.detail

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.dicodingeventapp.R
import com.example.dicodingeventapp.data.response.Event
import com.example.dicodingeventapp.databinding.ActivityDetailBinding
import java.text.SimpleDateFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]

        val eventId = intent.getIntExtra("EVENT_ID", -1)

        if (isNetworkAvailable()) {
            viewModel.getDetail(eventId.toString())
        } else {
            showAlertDialog()
        }


        viewModel.event.observe(this) {
            setDetailData(it)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setDetailData(event: Event) {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val date = inputFormat.parse(event.beginTime)
        val formattedDate = date?.let { outputFormat.format(it) }

        binding.nameDetail.text = event.name
        binding.categoryDetail.text = event.category
        binding.descriptionDetail.text = HtmlCompat.fromHtml(event.description, HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.ownerDetail.text = binding.root.context.getString(R.string.owner, event.ownerName)
        binding.locationDetail.text = binding.root.context.getString(R.string.location, event.cityName)
        binding.waktuDetail.text = binding.root.context.getString(R.string.begin, formattedDate)
        binding.quotaDetail.text = binding.root.context.getString(R.string.quota_detail, event.quota - event.registrants)
        Glide.with(binding.imgDetail.context)
            .load(event.mediaCover)
            .into(binding.imgDetail)

        binding.btnRegis.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(event.link)
            startActivity(intent)
        }
    }

    private fun showLoading(isLoading: Boolean) {
       binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.tidak_ada_koneksi_internet))
        builder.setMessage(getString(R.string.message_internet))
        builder.setPositiveButton("OK") { dialog, _ -> finish() }
        val dialog = builder.create()
        dialog.show()
    }
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork?.let {
                connectivityManager.getNetworkCapabilities(it)
            }
            return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}