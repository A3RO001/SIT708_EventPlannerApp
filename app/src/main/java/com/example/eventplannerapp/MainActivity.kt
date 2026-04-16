package com.example.eventplannerapp

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.eventplannerapp.database.AppDatabase
import com.example.eventplannerapp.ui.fragments.AddEventFragment
import com.example.eventplannerapp.ui.fragments.EventListFragment

lateinit var db: AppDatabase

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize DB
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "event-db"
        ).allowMainThreadQueries().build()

        setContentView(R.layout.activity_main)

        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnList = findViewById<Button>(R.id.btnList)


        // Default screen (VERY IMPORTANT)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, AddEventFragment())
            .commit()

        // ADD EVENT button
        btnAdd.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, AddEventFragment())
                .commit()
        }

        // VIEW EVENTS button
        btnList.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, EventListFragment())
                .commit()
        }
    }
}