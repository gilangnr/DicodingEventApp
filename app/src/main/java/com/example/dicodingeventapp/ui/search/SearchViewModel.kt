package com.example.dicodingeventapp.ui.search

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

class SearchViewModel: ViewModel() {
    private val _searchEvent = MutableLiveData<List<ListEventsItem>>()
    val searchEvent: LiveData<List<ListEventsItem>> get() = _searchEvent
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    companion object {
        private const val TAG = "SearchViewModel"
    }
    fun searchEvent(q: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchEvents(q)
        client.enqueue(object : Callback<ResponseListEvent> {
            override fun onResponse(
                call: Call<ResponseListEvent>,
                response: Response<ResponseListEvent>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _searchEvent.value = response.body()?.listEvents
                } else {
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseListEvent>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}