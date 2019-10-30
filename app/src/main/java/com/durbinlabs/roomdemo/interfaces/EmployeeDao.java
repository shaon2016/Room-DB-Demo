package com.durbinlabs.roomdemo.interfaces;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.durbinlabs.roomdemo.model.Employee;

import java.util.List;

/**
 * Created by hp on 12/27/2017.
 */

@Dao
public interface EmployeeDao {
    /*
        Here we can use table name. But I didn't specify any table name
        in my employee model class. That's why I am using model class
        instead of table name. Here Employee model class works as a table. I defined it as
        a entity. Room will understand and it will act as a table.
    */
    @Query("SELECT * FROM employee")
    List<Employee> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Employee... employees);

    @Query("SELECT * from employee where id = :id")
    Employee findAUser(int id);

    @Delete
    void delete(Employee employee);

    @Query("delete from employee")
    void removeAllEmployees();
}
