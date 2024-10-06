package com.example.dicodingevent.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.dicodingevent.data.database.FavEventRoomDatabase
import com.example.dicodingevent.data.database.FavoriteEvent
import com.example.dicodingevent.data.database.FavoriteEventDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavEventRepository(application:Application)
{
    private lateinit var mFavEvent:FavoriteEventDao
    private val executorService:ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavEventRoomDatabase.getDatabase(application)
        mFavEvent = db.noteDao()
    }

    fun getAllFavEvent(): LiveData<List<FavoriteEvent>>
    {
        return  mFavEvent.getAllFavEvent()
    }

    fun insert(favEvent:FavoriteEvent)
    {
        executorService.execute { mFavEvent.insert(favEvent) }
    }

    fun delete(favEvent:FavoriteEvent)
    {
        executorService.execute { mFavEvent.delete(favEvent) }
    }
}
