package com.durbinlabs.roomdemo.database

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.durbinlabs.roomdemo.interfaces.ClientDao
import com.durbinlabs.roomdemo.model.Client

/**
 * Created by Shaon on 12/27/2017.
 */

@Database(entities = [Client::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun clientDao(): ClientDao

    companion object {
        private val DB_NAME = "appDatabase.db"
        @Volatile
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = create(context)
            }
            return instance!!
        }

        private fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}
