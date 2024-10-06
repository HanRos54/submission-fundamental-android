package com.example.dicodingevent.ui.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.database.FavoriteEvent
import com.example.dicodingevent.data.repository.FavEventRepository

class FavoriteAddViewModel(application:Application): ViewModel()
{
    private val mFavRepository:FavEventRepository = FavEventRepository(application)


    fun insert(favEvent: FavoriteEvent)
    {
        mFavRepository.insert(favEvent)
    }

    fun delete(favEvent: FavoriteEvent)
    {
        mFavRepository.delete(favEvent)
    }
}