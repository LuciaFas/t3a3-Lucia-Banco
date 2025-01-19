package com.example.banco_lufaga.bd

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.banco_lufaga.dao.CajeroDAO
import com.example.banco_lufaga.pojo.CajeroEntity

@Database(entities = [CajeroEntity::class], version = 1)
abstract class CajeroDatabase : RoomDatabase() {
    abstract fun cajeroDao(): CajeroDAO
}
