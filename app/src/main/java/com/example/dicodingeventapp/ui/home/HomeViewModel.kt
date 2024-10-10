package com.example.dicodingeventapp.ui.home

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
import com.example.dicodingeventapp.ui.upcoming.UpcomingViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val eventRepository: EventRepository) : ViewModel() {
    private val _upcomingEvents = MediatorLiveData<List<ListEventsItem>>()
    val upcomingEvents: LiveData<List<ListEventsItem>> get() = _upcomingEvents

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    companion object {
        private const val TAG = "HomeViewModel"
    }

    fun loadUpcoming5() {
        _isLoading.value = true

        val result = eventRepository.getUpcoming5()

        _upcomingEvents.addSource(result) { result ->
            when (result) {
                is Result.Loading -> _isLoading.value = true
                is Result.Success -> {
                    _isLoading.value = false
                    _upcomingEvents.value = result.data
                }
                is Result.Error -> {
                    _isLoading.value = false
                    _errorMessage.value = result.error
                    Log.d(TAG, "Error fetching events: ${result.error}")
                }
            }
        }
    }


//    fun loadFinishedEvents() {
//        _isLoading.value = true
//        val client = ApiConfig.getApiService().getFinished5()
//        client.enqueue(object : Callback<ResponseListEvent> {
//            override fun onResponse(
//                call: Call<ResponseListEvent>,
//                response: Response<ResponseListEvent>
//            ) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    Log.d(TAG, "Number of events: ${response.body()?.listEvents?.size}")
//                    _finishedEvents.value = response.body()?.listEvents
//                } else {
//                    Log.e(TAG,"onFailure: ${response.message()}")
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseListEvent>, t: Throwable) {
//                _isLoading.value = false
//                Log.e(TAG, "onFailure: ${t.message.toString()}")
//            }
//
//        })
//    }
}
