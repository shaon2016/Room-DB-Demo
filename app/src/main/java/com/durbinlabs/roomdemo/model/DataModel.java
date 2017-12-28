package com.durbinlabs.roomdemo.model;

/**
 * Created by hp on 12/28/2017.
 */

public class DataModel {
    private Client client;
    private Book book;

    public DataModel(Client client, Book book) {
        this.client = client;
        this.book = book;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
