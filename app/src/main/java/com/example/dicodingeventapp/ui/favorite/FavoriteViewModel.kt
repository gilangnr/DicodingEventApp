package com.example.dicodingeventapp.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingeventapp.data.EventRepository
import com.example.dicodingeventapp.data.local.FavoriteRepository
import com.example.dicodingeventapp.data.local.entity.FavoriteEvent

class FavoriteViewModel(application: Application) : ViewModel() {
    private val favoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun getAllFav(): LiveData<List<FavoriteEvent>> {
        return favoriteRepository.getAllFavEvent()
    }

}