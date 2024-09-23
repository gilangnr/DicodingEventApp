package com.example.dicodingeventapp.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.response.ListEventsItem
import com.example.dicodingeventapp.data.response.ResponseListEvent
import com.example.dicodingeventapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _upcomingEvents = MutableLiveData<List<ListEventsItem>>()
    val upcomingEvents: LiveData<List<ListEventsItem>> get() = _upcomingEvents
    private val _finishedEvents = MutableLiveData<List<ListEventsItem>>()
    val finishedEvents: LiveData<List<ListEventsItem>> get() = _finishedEvents
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    companion object {
        private const val TAG = "HomeViewModel"
    }
    fun loadUpcomingEvents() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUpcoming5()
        client.enqueue(object : Callback<ResponseListEvent> {
            override fun onResponse(
                call: Call<ResponseListEvent>,
                response: Response<ResponseListEvent>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d(TAG, "Number of events: ${response.body()?.listEvents?.size}")
                    _upcomingEvents.value = response.body()?.listEvents
                } else {
                    Log.e(TAG,"onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseListEvent>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun loadFinishedEvents() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFinished5()
        client.enqueue(object : Callback<ResponseListEvent> {
            override fun onResponse(
                call: Call<ResponseListEvent>,
                response: Response<ResponseListEvent>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d(TAG, "Number of events: ${response.body()?.listEvents?.size}")
                    _finishedEvents.value = response.body()?.listEvents
                } else {
                    Log.e(TAG,"onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseListEvent>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}
