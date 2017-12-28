package com.durbinlabs.roomdemo.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.durbinlabs.roomdemo.R;
import com.durbinlabs.roomdemo.adapters.RecyclerViewAdapter;
import com.durbinlabs.roomdemo.database.AppDatabase;
import com.durbinlabs.roomdemo.model.Book;
import com.durbinlabs.roomdemo.model.Client;
import com.durbinlabs.roomdemo.model.DataModel;
import com.durbinlabs.roomdemo.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private AppDatabase db;
    private List<Client> clients;
    private List<Book> books;
    private RecyclerView rv;
    private Button btnAdd, btnRemoveAll;
    private EditText evName, evAge;
    private RecyclerViewAdapter adapter;
    private List<DataModel> modelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        // Getting instance of our DB
        db = AppDatabase.getInstance(getApplicationContext());
        // Inserting data to the tables
        clients = new ArrayList<>();
        modelList = new ArrayList<>();
        books = new ArrayList<>();
        insert();

        evName = findViewById(R.id.evAddName);
        evAge = findViewById(R.id.evAddAge);
        btnAdd = findViewById(R.id.btnAdd);
        btnRemoveAll = findViewById(R.id.btnRemoveAll);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewData();
                adapter.clear();

                MyDataLoadAsyncTask myDataLoadAsyncTask = new MyDataLoadAsyncTask();
                myDataLoadAsyncTask.execute();
            }
        });

        btnRemoveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearRecyclerView();
            }
        });
    }

    private void addNewData() {
        //TODO
        String name = evName.getText().toString();
        int age = parseInt(evAge.getText().toString(), -1);

        final Client client = new Client(new Random().nextInt(), name, age, 20);

        if (name.equals("")) {
            evName.setError("Name cant be empty");
            return;
        }
        if (age < 0) {
            evAge.setError("Age cant be empty. It must be a numeric value");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                db.clientDao().insert(client);
            }
        }).start();


    }

    public static int parseInt(String numStr, int defValue) {
        try {
            return Integer.parseInt(numStr);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
            return defValue;
        }
    }

    private void insert() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.clientDao().insert(new Client(1, "Shaon", 25, 5000));
                db.clientDao().insert(new Client(2, "Ashiq", 26, 6000));

                db.bookDao().insert(new Book("The Alchemist", 2, 1));
                db.bookDao().insert(new Book("The Alchemist", 5, 2));

                MyDataLoadAsyncTask myDataLoadAsyncTask = new MyDataLoadAsyncTask();
                myDataLoadAsyncTask.execute();
            }
        }).start();
    }

    private void clearRecyclerView() {
        // clearing DB
        new Thread(new Runnable() {
            @Override
            public void run() {
                //TODO remove
                db.clientDao().removeAllClients();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                    }
                });
                MyDataLoadAsyncTask myDataLoadAsyncTask = new MyDataLoadAsyncTask();
                myDataLoadAsyncTask.execute();
            }
        }).start();
    }

    protected class MyDataLoadAsyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            clients = db.clientDao().getAll();
            for (Client client : clients) {
                books.add(db.bookDao().getAllById(client.getId()));
                Log.d(TAG, "Size of book: " + books.size() + "");
            }

            for (int i = 0; i < clients.size(); i++) {
                modelList.add(new DataModel(clients.get(i), books.get(i)));
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            adapter = new RecyclerViewAdapter(modelList, MainActivity.this);
            rv.setAdapter(adapter);
        }
    }
}
