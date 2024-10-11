package com.example.dicodingeventapp.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.dicodingeventapp.data.local.entity.FavoriteEvent
import com.example.dicodingeventapp.data.local.room.FavoriteEventDao
import com.example.dicodingeventapp.data.local.room.FavoriteEventDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private lateinit var favoriteEventDao: FavoriteEventDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteEventDatabase.getDatabase(application)
        favoriteEventDao = db.favoriteDao()
    }

    fun getAllFavEvent(): LiveData<List<FavoriteEvent>> {
        return favoriteEventDao.getAllFavorite()
    }

    fun insert(favoriteEvent: FavoriteEvent) {
        executorService.execute { favoriteEventDao.insert(favoriteEvent) }
    }

    fun delete(favoriteEvent: FavoriteEvent) {
        executorService.execute { favoriteEventDao.delete(favoriteEvent) }
    }
}