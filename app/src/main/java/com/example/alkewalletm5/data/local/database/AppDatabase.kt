package com.example.alkewalletm5.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.alkewalletm5.data.local.dao.TransactionDAO
import com.example.alkewalletm5.data.local.dao.UserDAO
import com.example.alkewalletm5.data.local.model.Transaction
import com.example.alkewalletm5.data.local.model.User



@Database(entities = [User::class, Transaction::class], version = 1, exportSchema = false)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun userdao(): UserDAO
    abstract fun transactionDao(): TransactionDAO
    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "alkewallet_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}