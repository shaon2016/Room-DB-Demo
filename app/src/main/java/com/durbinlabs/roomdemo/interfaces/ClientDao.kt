package com.durbinlabs.roomdemo.interfaces


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.durbinlabs.roomdemo.model.Client

/**
 * Created by hp on 12/27/2017.
 */

@Dao
interface ClientDao {
    @Query("SELECT * FROM client")
     fun all(): LiveData<List<Client>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg clients: Client)

    @Query("DELETE FROM client")
    suspend fun removeAllClients()
}
