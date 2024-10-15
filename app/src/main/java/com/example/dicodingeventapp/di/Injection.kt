package com.example.dicodingeventapp.di

import android.content.Context
import com.example.dicodingeventapp.data.EventRepository
import com.example.dicodingeventapp.data.local.room.FavoriteEventDatabase
import com.example.dicodingeventapp.data.remote.retrofit.ApiConfig

object Injection  {
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService()
        val database = FavoriteEventDatabase.getDatabase(context)
        val dao = database.favoriteDao()
        return EventRepository.getInstance(apiService, dao)
    }

}