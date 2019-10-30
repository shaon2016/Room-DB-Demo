package com.durbinlabs.roomdemo.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * Created by hp on 12/28/2017.
 */

@Entity(foreignKeys = @ForeignKey(entity = Client.class, parentColumns = "id", childColumns =
        "user_id", onDelete = ForeignKey.CASCADE))
public class Book {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @ColumnInfo(name = "total_book")
    private int totalBook;
    @ColumnInfo(name = "user_id")
    private int userId;

    public Book(String name, int totalBook, int userId) {
        this.name = name;
        this.totalBook = totalBook;
        this.userId = userId;
    }

    @Ignore
    public Book(int totalBook) {
        this.totalBook = totalBook;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalBook() {
        return totalBook;
    }

    public void setTotalBook(int totalBook) {
        this.totalBook = totalBook;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
