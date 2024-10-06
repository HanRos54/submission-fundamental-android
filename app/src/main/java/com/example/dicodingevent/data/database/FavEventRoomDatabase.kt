package com.example.dicodingevent.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEvent::class], version = 1)
abstract class FavEventRoomDatabase : RoomDatabase()
{
    abstract fun noteDao():FavoriteEventDao

    companion object {
        @Volatile
        private var INSTANCE:FavEventRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context:Context):FavEventRoomDatabase
        {
            if (INSTANCE == null)
            {
                synchronized(FavEventRoomDatabase::class.java)
                {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavEventRoomDatabase::class.java, "event_database"
                        ).build()
                }
            }
            return INSTANCE as FavEventRoomDatabase
        }
    }
}