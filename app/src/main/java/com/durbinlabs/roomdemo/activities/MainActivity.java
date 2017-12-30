package com.durbinlabs.roomdemo.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.durbinlabs.roomdemo.R;
import com.durbinlabs.roomdemo.adapters.RecyclerViewAdapter;
import com.durbinlabs.roomdemo.database.AppDatabase;
import com.durbinlabs.roomdemo.model.Book;
import com.durbinlabs.roomdemo.model.Client;
import com.durbinlabs.roomdemo.model.DataModel;
import com.durbinlabs.roomdemo.viewmodels.DataViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnLongClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private AppDatabase db;
    private List<Client> clients;
    private List<Book> books;
    private RecyclerView rv;
    private Button btnAdd, btnRemoveAll;
    private EditText evName, evAge, evTotalBook;
    private RecyclerViewAdapter adapter;
    private List<DataModel> modelList;
    private DataViewModel clientViewModel;

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
        books = new ArrayList<>();
        adapter = new RecyclerViewAdapter(modelList, this, this);
        rv.setAdapter(adapter);
        //insert();

        evName = findViewById(R.id.evAddName);
        evAge = findViewById(R.id.evAddAge);
        evTotalBook = findViewById(R.id.evAddTotalBookCount);
        btnAdd = findViewById(R.id.btnAdd);
        btnRemoveAll = findViewById(R.id.btnRemoveAll);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewData();
            }
        });

        btnRemoveAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearRecyclerView();
            }
        });

        clientViewModel = ViewModelProviders.of(MainActivity.this).get(DataViewModel.class);
        clientViewModel.getClientList().observe(MainActivity.this, new Observer<List<DataModel>>() {
            @Override
            public void onChanged(@Nullable List<DataModel> models) {
                adapter.addDataToDataModel(models);
            }
        });
    }

    private void addNewData() {
        String name = evName.getText().toString();
        int age = parseInt(evAge.getText().toString(), -1);
        final int totalBook = parseInt(evTotalBook.getText().toString(), -1);

        boolean valid = validateInput(name, age, totalBook);
        if (!valid) return;
        final Client client = new Client(name, age);

        /*
        first inserting the client.
        second getting the client id
        then inserting book number using that id
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.clientDao().insert(client);
                Client lastClient = db.clientDao().getLastRow();
                Book book = new Book("The Alchemist", totalBook, lastClient.getId());
                db.bookDao().insert(book);
                // Data is being inserted in the background. That time data may not be got by the
                // asynctask
            }
        }).start();
    }

    private boolean validateInput(String name, int age, int totalBook) {
        if (name.equals("")) {
            evName.setError("Name cant be empty");
            return false;
        }
        if (age < 0) {
            evAge.setError("Age cant be empty. It must be a numeric value");
            return false;
        }
        if (totalBook < 0) {
            evAge.setError("Total book cant be empty. It must be a numeric value");
            return false;
        }

        return true;
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
                Client lastClient;
                db.clientDao().insert(new Client("Shaon", 25));
                lastClient = db.clientDao().getLastRow();

                db.bookDao().insert(new Book("The Alchemist", 67, lastClient.getId()));

                db.clientDao().insert(new Client("Ashiq", 26));
                lastClient = db.clientDao().getLastRow();

                db.bookDao().insert(new Book("The Alchemist", 65, lastClient.getId()));

            }
        }).start();
    }

    @Override
    public boolean onLongClick(View view) {
        Client client = (Client) view.getTag();
        clientViewModel.deleteClient(client);
        return true;
    }

    private void clearRecyclerView() {
        clientViewModel.deleteAll();
    }

}
