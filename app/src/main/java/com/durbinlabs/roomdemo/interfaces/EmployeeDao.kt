package com.durbinlabs.roomdemo.interfaces


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.durbinlabs.roomdemo.model.Employee

/**
 * Created by hp on 12/27/2017.
 */

@Dao
interface EmployeeDao {
    /*
        Here we can use table name. But I didn't specify any table name
        in my employee model class. That's why I am using model class
        instead of table name. Here Employee model class works as a table. I defined it as
        a entity. Room will understand and it will act as a table.
    */
    @get:Query("SELECT * FROM employee")
    val all: List<Employee>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg employees: Employee)

    @Query("SELECT * from employee where id = :id")
    fun findAUser(id: Int): Employee

    @Delete
    fun delete(employee: Employee)

    @Query("delete from employee")
    fun removeAllEmployees()
}
