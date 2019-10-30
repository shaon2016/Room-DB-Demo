package com.durbinlabs.roomdemo.interfaces


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.durbinlabs.roomdemo.model.Client
import com.durbinlabs.roomdemo.model.ClientDataModel

/**
 * Created by hp on 12/27/2017.
 */

@Dao
interface ClientDao {
    @get:Query("SELECT * FROM client")
    val all: LiveData<List<Client>>

    @get:Query("SELECT * FROM client ORDER BY id DESC LIMIT 1")
    val lastRow: Client

    @get:Query(
        "SELECT c.id, c.client_name as name, c.client_age as age, b.total_book as totalBook " +
                "FROM client " +
                "c " +
                "inner join book b ON c.id = b.user_id"
    )
    val allWithBook: LiveData<List<ClientDataModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg clients: Client)

    @Query("SELECT * from client where id = :id")
    fun findAUser(id: Int): Client

    @Delete
    fun delete(client: Client)

    @Query("DELETE FROM client")
    fun removeAllClients()
}
