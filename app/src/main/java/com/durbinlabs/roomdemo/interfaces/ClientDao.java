package com.durbinlabs.roomdemo.interfaces;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.durbinlabs.roomdemo.model.Client;
import com.durbinlabs.roomdemo.model.ClientDataModel;

import java.util.List;

/**
 * Created by hp on 12/27/2017.
 */

@Dao
public interface ClientDao {
    @Query("SELECT * FROM client")
    LiveData<List<Client>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Client... clients);

    @Query("SELECT * from client where id = :id")
    Client findAUser(int id);

    @Delete
    void delete(Client client);

    @Query("DELETE FROM client")
    void removeAllClients();

    @Query("SELECT * FROM client ORDER BY id DESC LIMIT 1")
    Client getLastRow();

    @Query("SELECT c.id, c.client_name as name, c.client_age as age, b.total_book as totalBook " +
            "FROM client " +
            "c " +
            "inner join book b ON c.id = b.user_id")
    LiveData<List<ClientDataModel>> getAllWithBook();
}
