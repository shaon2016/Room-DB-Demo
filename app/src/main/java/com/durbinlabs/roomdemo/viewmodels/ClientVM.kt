package com.durbinlabs.roomdemo.viewmodels

import com.durbinlabs.roomdemo.interfaces.ClientDao
import com.durbinlabs.roomdemo.model.Client
import kotlinx.coroutines.launch

/**
 * Created by Shaon on 12/30/2017.
 */

class ClientVM constructor(val clientDao: ClientDao) : BaseVM() {

    fun insert(client: Client) = launch (coroutineContext){
        clientDao.insert(client)
    }

     val clients = clientDao.all()

//    val clients: LiveData<List<Client>> = clients_
//
//    fun all() = launch(coroutineContext) {
//        clients_.postValue(clientDao.all())
//    }


}
