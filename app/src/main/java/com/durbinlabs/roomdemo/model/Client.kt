package com.durbinlabs.roomdemo.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by hp on 12/27/2017.
 */

@Entity(tableName = "client")
class Client {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @ColumnInfo(name = "client_name")
    var name: String? = null
    @ColumnInfo(name = "client_age")
    var age: Int = 0
    @Ignore
    var book: Book? = null

    @Ignore
    constructor(id: Int, name: String, age: Int, book: Book) {
        this.id = id
        this.name = name
        this.age = age
        this.book = book
    }

    constructor(name: String, age: Int) {
        this.name = name
        this.age = age
    }
}
