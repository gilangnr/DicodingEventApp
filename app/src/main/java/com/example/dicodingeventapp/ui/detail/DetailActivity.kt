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
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.bumptech.glide.Glide
import com.example.dicodingeventapp.R
import com.example.dicodingeventapp.data.local.entity.FavoriteEvent
import com.example.dicodingeventapp.data.remote.response.Event
import com.example.dicodingeventapp.databinding.ActivityDetailBinding
import com.example.dicodingeventapp.ui.favorite.FavoriteViewModel
import java.text.SimpleDateFormat
import java.util.Locale

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var favAddViewModel: FavoriteAddViewModel

    private var isFavorite: Boolean = false
    private lateinit var currentEvent: Event
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        viewModel = ViewModelProvider(this, DetailFactory.getInstance(this)).get(DetailViewModel::class.java)
        favAddViewModel = ViewModelProvider(this, FavoriteAddFactory.getInstance(application)).get(FavoriteAddViewModel::class.java)

        val eventId = intent.getIntExtra("EVENT_ID", -1)

        if (isNetworkAvailable()) {
            viewModel.detailEvent(eventId.toString())
        } else {
            showAlertDialog()
        }


        viewModel.event.observe(this) {
            setDetailData(it)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        viewModel.errorMessage.observe(this) { error ->
            if (error != null) {
                Toast.makeText(this,  error, Toast.LENGTH_SHORT).show()
            }
        }

        binding.fabFavorite.setOnClickListener {
            if (isFavorite) {
                favAddViewModel.delete(FavoriteEvent(currentEvent.id.toString(), currentEvent.name, currentEvent.mediaCover ))
                Toast.makeText(this, "${currentEvent.name} dihapus dari favorit", Toast.LENGTH_SHORT).show()
            } else {
                favAddViewModel.insert(FavoriteEvent(currentEvent.id.toString(), currentEvent.name, currentEvent.mediaCover))
                Toast.makeText(this, "${currentEvent.name} ditambahkan ke favorit", Toast.LENGTH_SHORT).show()
            }
            isFavorite = !isFavorite
            updateFavButton()
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
        currentEvent = event


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

        checkFavorite(event.id)
    }

    private fun checkFavorite(eventId: Int) {
        favAddViewModel.getFavoriteEventById(eventId.toString()).observe(this) { favoriteEvent ->
            isFavorite = favoriteEvent != null
            updateFavButton()
        }
    }

    private fun updateFavButton() {
        binding.fabFavorite.setImageResource(if (isFavorite) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24)
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