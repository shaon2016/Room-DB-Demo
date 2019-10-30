package com.durbinlabs.roomdemo.viewmodels

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import com.durbinlabs.roomdemo.database.AppDatabase
import com.durbinlabs.roomdemo.model.Client
import com.durbinlabs.roomdemo.model.ClientDataModel

/**
 * Created by Shaon on 12/30/2017.
 */

class ClientDataViewModel(application: Application) : AndroidViewModel(application) {
    val clientList: LiveData<List<ClientDataModel>>
    private val db: AppDatabase


    init {

        db = AppDatabase.getInstance(this.getApplication())!!
        clientList = db.clientDao().allWithBook
    }

    fun deleteClient(client: Client) {
        SingleDeletionAsyncTask(db).execute(client)
    }

    private class SingleDeletionAsyncTask internal constructor(private val db: AppDatabase) :
        AsyncTask<Client, Void, Void>() {

        override fun doInBackground(vararg clients: Client): Void? {
            db.clientDao().delete(clients[0])
            return null
        }
    }

    fun deleteAll() {
        AllDeletionAsyncTask(db).execute()
    }

    private class AllDeletionAsyncTask internal constructor(private val db: AppDatabase) :
        AsyncTask<Void, Void, Void>() {

        override fun doInBackground(vararg voids: Void): Void? {
            db.clientDao().removeAllClients()
            return null
        }
    }
}
