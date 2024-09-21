package com.example.dicodingeventapp.ui.upcoming

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

class UpcomingViewModel : ViewModel() {

    private val _events = MutableLiveData<List<ListEventsItem>>()
    val events: LiveData<List<ListEventsItem>> get() = _events

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    companion object {
        private const val TAG = "UpcomingViewModel"
    }

    fun loadUpcomingEvents() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUpcoming()
        client.enqueue(object : Callback<ResponseListEvent> {
            override fun onResponse(
                call: Call<ResponseListEvent>,
                response: Response<ResponseListEvent>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.d("UpcomingFragment", "Number of events: ${response.body()?.listEvents?.size}")
                    _events.value = response.body()?.listEvents
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