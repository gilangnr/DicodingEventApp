package com.example.dicodingeventapp.ui.upcoming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UpcomingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Upcoming Event Fragment"
    }
    val text: LiveData<String> = _text
}