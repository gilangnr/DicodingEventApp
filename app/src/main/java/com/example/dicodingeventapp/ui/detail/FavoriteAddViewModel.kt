package com.example.dicodingeventapp.ui.detail

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.local.FavoriteRepository
import com.example.dicodingeventapp.data.local.entity.FavoriteEvent

class FavoriteAddViewModel(application: Application): ViewModel() {

    private val favoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favoriteEvent: FavoriteEvent) {
        favoriteRepository.insert(favoriteEvent)
    }

    fun delete(favoriteEvent: FavoriteEvent) {
        favoriteRepository.delete(favoriteEvent)
    }

   fun getFavoriteEventById(id: String): LiveData<FavoriteEvent> {
       return favoriteRepository.getFavoriteEventById(id)
   }
}