package com.durbinlabs.roomdemo

import android.content.Context
import com.durbinlabs.roomdemo.database.AppDatabase
import com.durbinlabs.roomdemo.interfaces.ClientDao
import spartons.com.androidroomcoroutines.ViewModelFactory

object Injection {

    private fun provideClientDataSource(context: Context): ClientDao {
        val database = AppDatabase.getInstance(context)
        return database.clientDao()
    }

    fun provideViewModelFactory(context: Context): ViewModelFactory {
        val taskDao = provideClientDataSource(context)
        return ViewModelFactory(taskDao)
    }
}