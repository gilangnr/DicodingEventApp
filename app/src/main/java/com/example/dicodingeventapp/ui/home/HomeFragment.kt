package com.example.dicodingeventapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.dicodingeventapp.databinding.FragmentHomeBinding
import com.example.dicodingeventapp.ui.detail.DetailActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var upcomingAdapter: HomeUpcomingAdapter
    private lateinit var finishedAdapter: HomeFinishedAdapter
    private lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        upcomingAdapter = HomeUpcomingAdapter { event ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("EVENT_ID", event.id)
            startActivity(intent)
        }
        finishedAdapter = HomeFinishedAdapter()
        binding.viewPagerUpcoming.adapter = upcomingAdapter
        binding.rvFinishedHome.adapter = finishedAdapter
        binding.rvFinishedHome.layoutManager = LinearLayoutManager(requireContext())

        viewModel.upcomingEvents.observe(viewLifecycleOwner) { events ->
            if (events != null && events.isNotEmpty()) {
                upcomingAdapter.submitList(events)
            } else {
                Log.d("HomeFragment", "No events available")
            }
        }

        viewModel.finishedEvents.observe(viewLifecycleOwner) { events ->
            finishedAdapter.submitList(events)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}