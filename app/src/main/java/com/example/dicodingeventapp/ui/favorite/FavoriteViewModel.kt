package com.example.dicodingeventapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.EventRepository
import com.example.dicodingeventapp.data.local.FavoriteRepository
import com.example.dicodingeventapp.data.local.entity.FavoriteEvent

class FavoriteViewModel(private val eventRepository: EventRepository) : ViewModel() {


   val favoriteEvent: LiveData<List<FavoriteEvent>> = eventRepository.getAllFavorite()

}