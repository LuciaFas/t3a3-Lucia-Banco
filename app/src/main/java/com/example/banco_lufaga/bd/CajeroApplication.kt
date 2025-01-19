package com.example.banco_lufaga.bd

import android.app.Application
import androidx.room.Room

class CajeroApplication : Application() {

    companion object{
        lateinit var database: CajeroDatabase
    }
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this,
            CajeroDatabase::class.java,
            "CajeroDatabase")
            .build()
    }
}
