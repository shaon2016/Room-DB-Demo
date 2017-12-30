package com.durbinlabs.roomdemo.model;

/**
 * Created by hp on 12/28/2017.
 */

public class ClientDataModel {
    private int id;
    private String name;
    private int age, totalBook;

    public ClientDataModel(int id, String name, int age, int totalBook) {
        this.id = id;
        this.name = name;
        this.age = age;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTotalBook() {
        return totalBook;
    }

    public void setTotalBook(int totalBook) {
        this.totalBook = totalBook;
    }
}
