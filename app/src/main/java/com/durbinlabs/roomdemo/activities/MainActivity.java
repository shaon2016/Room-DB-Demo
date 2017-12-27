package com.durbinlabs.roomdemo.activities;

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
import com.durbinlabs.roomdemo.model.Client;
import com.durbinlabs.roomdemo.model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private AppDatabase db;
    private List<Client> clients;
    private RecyclerView rv;
    private Button btnAdd, btnRemoveAll;
    private EditText evName, evAge;
    private RecyclerViewAdapter adapter;

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
                getAllData();
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
        String name = evName.getText().toString();
        int age = 0;
        if (!evAge.getText().toString().equals(""))
            age = Integer.parseInt(evAge.getText().toString());

        final Client client = new Client(new Random().nextInt(), name, age, 20);

        if (!name.equals("") && !evAge.getText().toString().equals(""))
            new Thread(new Runnable() {
                @Override
                public void run() {
                    db.clientDao().insert(client);
                }
            }).start();

    }

    private void getAllData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                clients = db.clientDao().getAll();
                Log.d(TAG, "Client table size: " + clients.size());

                loadDataToRecyclerView();
            }
        }).start();
    }

    private void insert() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.clientDao().insert(new Client(1, "Shaon", 25, 5000));
                db.clientDao().insert(new Client(2, "Ashiq", 26, 6000));

                getAllData();
            }
        }).start();
    }

    private void loadDataToRecyclerView() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (clients.size() > 0) {
                    adapter = new RecyclerViewAdapter(clients,
                            MainActivity.this);
                    rv.setAdapter(adapter);
                }
            }
        });
    }

    private void clearRecyclerView() {
        // clearing DB
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.clientDao().removeAllClients();
                getAllData();
            }
        }).start();
    }
}
