package com.example.eventplannerapp.database

import androidx.room.*
import com.example.eventplannerapp.model.Event

@Dao
interface EventDao {

    // CREATE
    @Insert
    fun insert(event: Event)

    // READ
    @Query("SELECT * FROM events ORDER BY datetime ASC")
    fun getAllEvents(): List<Event>

    // UPDATE
    @Update
    fun update(event: Event)

    // DELETE
    @Delete
    fun delete(event: Event)
}