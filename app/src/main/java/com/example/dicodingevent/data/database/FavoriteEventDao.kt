package com.example.dicodingevent.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteEventDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favEvent:FavoriteEvent)

    @Delete
    fun delete(favEvent:FavoriteEvent)

    @Query("Select * from FavoriteEvent ORDER BY id ASC")
    fun getAllFavEvent():LiveData<List<FavoriteEvent>>
}