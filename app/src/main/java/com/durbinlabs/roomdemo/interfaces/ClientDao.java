package com.durbinlabs.roomdemo.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.durbinlabs.roomdemo.model.Client;

import java.util.List;

/**
 * Created by hp on 12/27/2017.
 */

@Dao
public interface ClientDao {
    @Query("SELECT * FROM client")
    List<Client> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Client... clients);

    @Query("SELECT * from client where id = :id")
    Client findAUser(int id);

    @Delete
    void delete(Client client);

    @Query("delete from client")
    void removeAllClients();
}
