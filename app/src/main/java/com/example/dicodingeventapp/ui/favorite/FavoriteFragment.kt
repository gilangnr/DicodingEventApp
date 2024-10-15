package com.example.dicodingeventapp.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dicodingeventapp.databinding.FragmentFavoriteBinding
import com.example.dicodingeventapp.ui.detail.DetailActivity


class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter
    private val binding get() = _binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, FavoriteFactory.getInstance(requireContext())).get(FavoriteViewModel::class.java)

        adapter = FavoriteAdapter { favoriteEvent ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EVENT_ID", favoriteEvent.id.toInt())
            startActivity(intent)
        }

        binding?.rvFavorite?.adapter = adapter
        binding?.rvFavorite?.layoutManager = GridLayoutManager(requireContext(), 2)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.favoriteEvent.observe(viewLifecycleOwner) { favoriteEvents ->
            if (favoriteEvents == null ||favoriteEvents.isEmpty()) {
                Toast.makeText(requireContext(), "Tidak ada acara favorit", Toast.LENGTH_SHORT).show()
                binding?.rvFavorite?.visibility = View.GONE
            } else {
                adapter.submitList(favoriteEvents)
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}