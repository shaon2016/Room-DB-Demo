package com.durbinlabs.roomdemo.viewmodels

import androidx.lifecycle.*
import com.durbinlabs.roomdemo.interfaces.ClientDao
import com.durbinlabs.roomdemo.model.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * Created by Shaon on 10/30/2019.
 */

class ClientVM constructor(val clientDao: ClientDao) : ViewModel() {

    fun insert(client: Client) = viewModelScope.launch {
        clientDao.insert(client)
    }

     /*
     Used it with suspend function

     val clients  = liveData {
         val data = clientDao.all()
         emitSource(data)
     }

     */

    // The stream will run in io dispatcher
    val clients  = clientDao.all().flowOn(Dispatchers.IO).asLiveData()















}
