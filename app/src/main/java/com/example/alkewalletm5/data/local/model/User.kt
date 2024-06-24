package com.example.alkewalletm5.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val first_name: String = "",
    val last_name: String = "",
    val email: String = "",
    val password: String = ""
)