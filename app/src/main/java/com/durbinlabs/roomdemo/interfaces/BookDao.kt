package com.durbinlabs.roomdemo.interfaces


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

import com.durbinlabs.roomdemo.model.Book

/**
 * Created by hp on 12/28/2017.
 */

@Dao
interface BookDao {

    @get:Query("SELECT * FROM book")
    val all: List<Book>

    @Query("SELECT * FROM book where user_id = :userId")
    fun getAllById(userId: Int): Book

    @Query("SELECT total_book FROM book where user_id = :userId")
    fun getTotalBookById(userId: Int): Int


    @Insert
    fun insert(vararg books: Book)

}
