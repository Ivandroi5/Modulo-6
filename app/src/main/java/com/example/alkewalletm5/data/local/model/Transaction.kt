package com.example.alkewalletm5.data.local.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sender_name: String,
    val receiver_name: String,
    val transacion_date: String,
    val isReceiver : Boolean,
    val amount : Double,
    val concept : String
)