package com.durbinlabs.roomdemo.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by hp on 12/27/2017.
 */

@Entity(tableName = "client")
public class Client {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "client_name")
    private String name;
    @ColumnInfo(name = "client_age")
    private int age;
    @Ignore
    Book book;

    @Ignore
    public Client(int id, String name, int age, Book book) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.book = book;
    }

    public Client(String name, int age) {
        this.name = name;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
