package com.example.dicodingeventapp.data.local.room

import com.example.dicodingeventapp.data.local.entity.FavoriteEvent
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*

@Dao
interface EventDao {
    @Query("SELECT * FROM event")
    fun getFavorite() : LiveData<List<FavoriteEvent>>
}