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

import com.durbinlabs.roomdemo.R;
import com.durbinlabs.roomdemo.adapters.RecyclerViewAdapter;
import com.durbinlabs.roomdemo.database.AppDatabase;
import com.durbinlabs.roomdemo.model.Book;
import com.durbinlabs.roomdemo.model.Client;
import com.durbinlabs.roomdemo.model.DataModel;

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
    private EditText evName, evAge, evTotalBook;
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
        evTotalBook = findViewById(R.id.evAddTotalBookCount);
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
        String name = evName.getText().toString();
        int age = parseInt(evAge.getText().toString(), -1);
        final int totalBook = parseInt(evTotalBook.getText().toString(), -1);

        boolean valid = validateInput(name, age, totalBook);
        if (!valid) return;
        final Client client = new Client(name, age, 20);

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
                Log.d(TAG, "In add new data: client id: " + lastClient.getId());
                Book book = new Book("The Alchemist", totalBook, lastClient.getId());
                db.bookDao().insert(book);
                Log.d(TAG, "Size of book table: " + db.bookDao().getAll().size());
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
                db.clientDao().insert(new Client("Shaon", 25, 5000));
                lastClient = db.clientDao().getLastRow();
                Log.d(TAG, "In Insert: client id: " + lastClient.getId());
                db.bookDao().insert(new Book("The Alchemist", 67, lastClient.getId()));

                db.clientDao().insert(new Client("Ashiq", 26, 6000));
                lastClient = db.clientDao().getLastRow();
                Log.d(TAG, "In insert: client id: " + lastClient.getId());
                db.bookDao().insert(new Book("The Alchemist", 65, lastClient.getId()));

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
                Log.d(TAG, "do in background: client id " + client.getId());
                Log.d(TAG, "do in background: Total Book " + db.bookDao().getTotalBookById(client
                        .getId()));
            }

            for (int i = 0; i < clients.size(); i++) {
                modelList.add(new DataModel(clients.get(i), books.get(i)));
            }

            //TODO model list e data vul dekhacche ta thik kora
            /*database e data thikoi save hocche*/
            for (Client client : clients) {
                Book allById = db.bookDao().getAllById(client
                        .getId());
                Log.d(TAG, "In do in background: All book data , ID: " + allById.getId());
                Log.d(TAG, "In do in background: All book data, Total Book:  " + allById
                        .getTotalBook());
            }
// TODO need to check recycler view , why it loads falls data
// problem is . eta ekta data pele model list er position 1 howay data prothomtai nicche
            for (int i = 0; i < modelList.size(); i++) {
                Book allById = modelList.get(i).getBook();
                Log.d(TAG, "In do in background: All book data using data model , ID: " + allById
                        .getId
                                ());
                Log.d(TAG, "In do in background:All book data using data model, Total Book:  " + allById
                        .getTotalBook());
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
