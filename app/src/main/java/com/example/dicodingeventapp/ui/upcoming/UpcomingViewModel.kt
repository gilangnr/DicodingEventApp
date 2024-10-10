package com.example.dicodingeventapp.ui.upcoming

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.EventRepository
import com.example.dicodingeventapp.data.Result
import com.example.dicodingeventapp.data.remote.response.ListEventsItem
import com.example.dicodingeventapp.data.remote.response.ResponseListEvent
import com.example.dicodingeventapp.data.remote.retrofit.ApiConfig
import com.example.dicodingeventapp.ui.finished.FinishedViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel(private val eventRepository: EventRepository) : ViewModel() {

    private val _events = MediatorLiveData<List<ListEventsItem>>()
    val events: LiveData<List<ListEventsItem>> get() = _events

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    companion object {
        private const val TAG = "UpcomingViewModel"
    }


    fun loadUpcomingEvents() {
        _isLoading.value = true

        val result = eventRepository.loadUpcomingEvents()

        _events.addSource(result) { result ->
            when (result) {
                is Result.Loading -> _isLoading.value = true
                is Result.Success -> {
                    _isLoading.value = false
                    _events.value = result.data
                }
                is Result.Error -> {
                    _isLoading.value = false
                    _errorMessage.value = result.error
                    Log.d(TAG, "Error fetching events: ${result.error}")
                }
            }
        }
    }
}