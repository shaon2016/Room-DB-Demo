package com.durbinlabs.roomdemo.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by hp on 12/28/2017.
 */

@Entity(
    foreignKeys = [ForeignKey(
        entity = Client::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
class Book {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String? = null
    @ColumnInfo(name = "total_book")
    var totalBook: Int = 0
    @ColumnInfo(name = "user_id")
    var userId: Int = 0

    constructor(name: String, totalBook: Int, userId: Int) {
        this.name = name
        this.totalBook = totalBook
        this.userId = userId
    }

    @Ignore
    constructor(totalBook: Int) {
        this.totalBook = totalBook
    }
}
