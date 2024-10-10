package com.example.dicodingeventapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.EventRepository
import com.example.dicodingeventapp.data.local.entity.FavoriteEvent

class FavoriteViewModel(private val eventRepository: EventRepository) : ViewModel() {
    val favoriteEvents: LiveData<List<FavoriteEvent>> = eventRepository.getAllFavorite()

    fun addFavorite(event: FavoriteEvent) {
        eventRepository.addFavorite(event)
    }

    fun removeFavorite(event: FavoriteEvent) {
        eventRepository.removeFavorite(event)
    }
}