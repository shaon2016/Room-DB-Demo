package com.durbinlabs.roomdemo.interfaces;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

/**
 * Created by hp on 12/28/2017.
 */

@Dao
public interface BookDao {

    @Query("SELECT COUNT(*) FROM Book WHERE user_id = :userId")
    int bookNumberPerUser(int userId);
}
