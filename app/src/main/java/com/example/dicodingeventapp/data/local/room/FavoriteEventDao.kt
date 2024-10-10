package com.example.dicodingeventapp.data.local.room

import com.example.dicodingeventapp.data.local.entity.FavoriteEvent
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*

@Dao
interface FavoriteEventDao {
   @Insert(onConflict = OnConflictStrategy.IGNORE)
   fun insert(favoriteEvent: FavoriteEvent)

   @Delete
   fun delete(favoriteEvent: FavoriteEvent)

   @Query("SELECT * FROM FavoriteEvent ORDER BY id ASC")
   fun getAllFavorite(): LiveData<List<FavoriteEvent>>
}