package com.example.dicodingeventapp.ui.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingeventapp.data.EventRepository
import com.example.dicodingeventapp.di.Injection
import com.example.dicodingeventapp.ui.finished.FinishedViewModel

class FinishedFactory private constructor(private val eventRepository: EventRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FinishedViewModel::class.java)) {
            return FinishedViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: FinishedFactory? = null
        fun getInstance(context: Context): FinishedFactory =
            instance ?: synchronized(this) {
                instance ?: FinishedFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}