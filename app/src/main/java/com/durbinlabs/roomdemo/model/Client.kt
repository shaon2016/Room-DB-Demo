package com.durbinlabs.roomdemo.model


import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by hp on 12/27/2017.
 */
@Entity
class Client {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var name: String? = null
    var age: Int = 0
}
