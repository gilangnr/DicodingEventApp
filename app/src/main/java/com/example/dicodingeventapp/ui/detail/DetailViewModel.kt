package com.example.dicodingeventapp.ui.detail


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.response.Event
import com.example.dicodingeventapp.data.response.ResponseDetail
import com.example.dicodingeventapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _events = MutableLiveData<Event>()
    val event: LiveData<Event> get() = _events

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    companion object {
        private const val TAG = "DetailViewModel"
    }

    fun getDetail(id: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getEvent(id)
        client.enqueue(object : Callback<ResponseDetail> {
            override fun onResponse(
                call: Call<ResponseDetail>,
                response: Response<ResponseDetail>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _events.value = response.body()?.event
                } else {
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseDetail>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "onFailure: ${t.message.toString()}")
            }

        })

    }

}