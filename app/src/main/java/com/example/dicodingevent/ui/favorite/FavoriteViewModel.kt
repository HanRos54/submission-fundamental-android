package com.example.dicodingevent.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dicodingevent.data.database.FavoriteEvent
import com.example.dicodingevent.data.repository.FavEventRepository

class FavoriteViewModel(application:Application) : ViewModel()
{
    private val mFavRepository: FavEventRepository = FavEventRepository(application)

    fun getAllFav(): LiveData<List<FavoriteEvent>>
    {
        return mFavRepository.getAllFavEvent()
    }
}