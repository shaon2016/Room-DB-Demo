package com.durbinlabs.roomdemo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Shaon on 12/27/2017.
 */

// we can specify it as a table using below code
// @Entity(tableName = "employee")
@Entity
class Employee(
    @field:PrimaryKey(autoGenerate = false)
    var id: Int, @field:ColumnInfo(name = "emp_name")
    var name: String?, @field:ColumnInfo(name = "emp_salary")
    var salary: Int, @field:ColumnInfo(name = "employee_expense")
    var expense: Int
)
