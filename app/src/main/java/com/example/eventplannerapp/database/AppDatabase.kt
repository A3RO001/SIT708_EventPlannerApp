package com.example.eventplannerapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.eventplannerapp.model.Event

@Database(entities = [Event::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
}