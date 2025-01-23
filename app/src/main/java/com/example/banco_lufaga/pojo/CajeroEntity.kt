package com.example.banco_lufaga.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "cajeros")
class CajeroEntity (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo var direccion : String?,
    @ColumnInfo var latitud : Double?,
    @ColumnInfo var longitud : Double?,
    @ColumnInfo var zoom : String?


): Serializable