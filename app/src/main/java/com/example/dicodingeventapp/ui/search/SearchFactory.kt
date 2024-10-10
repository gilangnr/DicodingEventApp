package com.example.dicodingeventapp.ui.search

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingeventapp.data.EventRepository
import com.example.dicodingeventapp.di.Injection

class SearchFactory private constructor(private val eventRepository: EventRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: SearchFactory? = null
        fun getInstance(context: Context): SearchFactory =
            instance ?: synchronized(this) {
                instance ?: SearchFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}