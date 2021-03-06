package com.durbinlabs.roomdemo.interfaces;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.durbinlabs.roomdemo.model.Book;

import java.util.List;

/**
 * Created by hp on 12/28/2017.
 */

@Dao
public interface BookDao {

    @Query("SELECT * FROM book")
    List<Book> getAll();

    @Query("SELECT * FROM book where user_id = :userId")
    Book getAllById(int userId);

    @Query("SELECT total_book FROM book where user_id = :userId")
    int getTotalBookById(int userId);


    @Insert
    void insert(Book... books);

}
