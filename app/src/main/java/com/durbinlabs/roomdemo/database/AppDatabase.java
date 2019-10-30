package com.durbinlabs.roomdemo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.durbinlabs.roomdemo.interfaces.BookDao;
import com.durbinlabs.roomdemo.interfaces.ClientDao;
import com.durbinlabs.roomdemo.interfaces.EmployeeDao;
import com.durbinlabs.roomdemo.model.Book;
import com.durbinlabs.roomdemo.model.Client;
import com.durbinlabs.roomdemo.model.Employee;

/**
 * Created by Shaon on 12/27/2017.
 */

@Database(entities = {Employee.class, Client.class, Book.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "appDatabase.db";
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static AppDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME).build();
    }

    public abstract EmployeeDao employeeDao();

    public abstract ClientDao clientDao();

    public abstract BookDao bookDao();
}
