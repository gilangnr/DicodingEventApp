package com.example.dicodingeventapp.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingeventapp.databinding.ActivitySearchBinding
import com.example.dicodingeventapp.ui.detail.DetailActivity
import com.example.dicodingeventapp.ui.home.HomeFinishedAdapter

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private lateinit var adapter: HomeFinishedAdapter
    private lateinit var viewModel: SearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        adapter = HomeFinishedAdapter{ event ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            startActivity(intent)
        }

        binding.rvSearch.adapter = adapter
        binding.rvSearch.layoutManager = LinearLayoutManager(this)

        binding.searchView.setupWithSearchBar(binding.searchBar)


        binding.searchView.editText.setOnEditorActionListener { textView, actionId, event ->
            val query = textView.text.toString()
            if (query.isNotEmpty()) {
                viewModel.searchEvent(query)
            }
            true
        }

        viewModel.searchEvent.observe(this) { events ->
            adapter.submitList(events)

            if (events.isEmpty()) {
                showAlertDialog()
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            showLoading(isLoading)
        }


    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Event tidak ditemukan")
        builder.setMessage("Maaf, tidak ada event yang sesuai dengan pencarian Anda.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}