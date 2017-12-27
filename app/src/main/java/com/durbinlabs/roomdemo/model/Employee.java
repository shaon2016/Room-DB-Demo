package com.durbinlabs.roomdemo.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Shaon on 12/27/2017.
 */

// we can specify it as a table using below code
// @Entity(tableName = "employee")
@Entity
public class Employee {
    @PrimaryKey(autoGenerate = false)
    private int id;
    @ColumnInfo(name = "emp_name")
    private String name;
    @ColumnInfo(name = "emp_salary")
    private int salary;
    @ColumnInfo(name = "employee_expense")
    private int expense;

    public Employee(int id, String name, int salary, int expense) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.expense = expense;
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

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }
}
