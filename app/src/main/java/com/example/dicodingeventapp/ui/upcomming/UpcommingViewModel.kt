package com.example.dicodingeventapp.ui.upcomming

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UpcommingViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Upcomming Event Fragment"
    }
    val text: LiveData<String> = _text
}