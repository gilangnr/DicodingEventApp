package com.example.dicodingeventapp.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.EventRepository
import com.example.dicodingeventapp.data.local.entity.FavoriteEvent

class FavoriteViewModel(private val eventRepository: EventRepository) : ViewModel() {

   private val _isLoading = MutableLiveData<Boolean>()
   val isLoading: LiveData<Boolean> get() = _isLoading

   private val _favoriteEvent = MutableLiveData<List<FavoriteEvent>>()
   val favoriteEvent: LiveData<List<FavoriteEvent>> get() = _favoriteEvent

   init {
       loadFavoriteEvents()
   }

   private fun loadFavoriteEvents() {
      _isLoading.value = true
      eventRepository.getAllFavorite().observeForever{events ->
         _isLoading.value = false
         _favoriteEvent.value = events
      }
   }

}