# Room Persistence Library

বিসমিল্লাহির রহমানির রহিম :)

কেন আমরা ROOM ব্যবহার করব? 

### GOOGLE highly recommend to use Room instead of SQLite

রুম হচ্ছে SQLite DB এর একটি এবস্ট্রাকশন লেয়ার, যেটি দিয়ে DB থেকে খুব সহজেই ডাটা পাওয়া যায়।

রুম ব্যবহার করে আমরা বয়লারপ্লেট কোড কমাতে পারি। ইন্সার্টন  এবং ডিলেশন ব্যাপারগুলো খুব সহজ করা হয়েছে এখানে। এটি ডাটা ক্যাশ করে রাখে যার ফলে এপের ডাটা গুলোর ফুল কপি সহজেই লোড হয়। এপ হয় ফাস্ট।

রুম লাইব্রেরী মডেল ক্লাস ব্যবহার করে ডাটাবেজ তৈরি করে, যার ফলে এক মডেল ক্লাস দিয়ে আমরা রিসাইকেলারে ডাটা লোড + ডাটাবেজ তৈরি দুইটি কাজই করতে পারি। মডেল ক্লাসের যে ফিল্ড গুলো আমাদের  ডাটাবেজে দরকার নেই সেগুলো আমরা @Ignore এনোটেশন ব্যবহার করে বাদ দিতে পারি।

নিচের ডিপেন্ডেন্সি গুলো এড করি আমাদের নতুন প্রজেক্টে,

```
implementation 'com.android.support:appcompat-v7:26.1.0'
implementation 'com.android.support:recyclerview-v7:26.1.0'
// Room
implementation "android.arch.persistence.room:runtime:1.0.0"
annotationProcessor "android.arch.persistence.room:compiler:1.0.0"

// ViewModel and LiveData
implementation "android.arch.lifecycle:extensions:1.0.0"
annotationProcessor "android.arch.lifecycle:compiler:1.0.0"
```

ক্লায়েন্ট মডেল ক্লাস। উপরের এন্টিটি ট্যাগ ব্যবহার করে আমরা নির্দেশ দিলাম যে এটি একটি টেবিল। টেবলনেইম, কলাম ইনফো এট্রিবিউট গুলো ব্যবহার করা ডিফল্ট। এগুলো ব্যবহার না করলে মডেল ক্লাস অনুসারে টেবিল তৈরি হবে এবং ভেরিয়েবল অনুসারে টেবিলের এন্টিটি শুরু হবে।

গেটার সেটার মেথোড রাখা জরূরী, ডাটা গেট করার জন্যে।

```
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


/**
 * Created by Shaon on 12/27/2017.
 */

@Entity(tableName = "client")
public class Client {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "client_name")
    private String name;
    @ColumnInfo(name = "client_age")
    private int age;
    @Ignore
    Book book;

    @Ignore
    public Client(int id, String name, int age, Book book) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.book = book;
    }

    public Client(String name, int age) {
        this.name = name;
        this.age = age;
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
```

### বুক মডেল ক্লাস।

ফরেইন কি ব্যবহার করে আমরা ক্লায়েন্ট মডেল ক্লাসের সাথে একটি রিলেশনশিপ স্থাপন করলাম।

এখানে প্যারেন্ট কলাম হচ্ছে ক্লায়েন্ট টেবিলের আইডি এবং চাইল্ড কলাম হবে বুক টেবিলে থাকা ক্লায়েন্ট আইডির কলাম টি। ক্যাসকেড দিয়ে আমরা বুক টেবিলের ডিলেটের কাজটি করেছি। ক্লায়েন্ট টেবিল থেকে কোন ক্লায়েন্ট ডিলিট হলে তার সব গুলো বুক টেবিলের ডাটাও ডিলেট হবে। ক্লায়েন্ট নাই তাই তার বুক ডাটাবেজে না থাকাই স্বাভাবিক :)

```
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


/**
 * Created by Shaon on 12/28/2017.
 */

@Entity(foreignKeys = @ForeignKey(entity = Client.class, parentColumns = "id", childColumns =
        "user_id", onDelete = ForeignKey.CASCADE))
public class Book {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    @ColumnInfo(name = "total_book")
    private int totalBook;
    @ColumnInfo(name = "user_id")
    private int userId;

    public Book(String name, int totalBook, int userId) {
        this.name = name;
        this.totalBook = totalBook;
        this.userId = userId;
    }

    @Ignore
    public Book(int totalBook) {
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

    public int getTotalBook() {
        return totalBook;
    }

    public void setTotalBook(int totalBook) {
        this.totalBook = totalBook;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
```

recyclerview তে ডাটা লোড করার জন্যে ক্লায়েন্ট এবং বুক মডেলের কম্বিনেশন। 

```
/**
 * Created by Shaon on 12/28/2017.
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
```
## DAO (Data Access Object)

ডাটাবেজ থেকে সকল ধরণের ডাটা এক্সেসের কাজ DAO তে করা হবে। 

## LiveData: 
একটি অবজারভেবল ডাটাহোল্ডার। যে একটিভিটি বা ফ্রেগমেন্টের জন্যে ডাটা লাইভডাটা ব্যবহার করে লোড করা হয়, সেই ডাটাতে কোন পরিবর্তন আসলে এটি সাথে সাথে UI আপডেট করে। 

DAO ইন্টারফেস বা এবস্ট্রাক্ট ব্যবহার করে তৈরি করা যেতে। See doc for details. :p

```
import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.durbinlabs.roomdemo.model.Client;
import com.durbinlabs.roomdemo.model.ClientDataModel;

import java.util.List;

/**
 * Created by Shaon on 12/27/2017.
 */

@Dao
public interface ClientDao {
    @Query("SELECT * FROM client")
    LiveData<List<Client>> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Client... clients);

    @Query("SELECT * from client where id = :id")
    Client findAUser(int id);

    @Delete
    void delete(Client client);

    @Query("DELETE FROM client")
    void removeAllClients();

    @Query("SELECT * FROM client ORDER BY id DESC LIMIT 1")
    Client getLastRow();

    @Query("SELECT c.id, c.client_name as name, c.client_age as age, b.total_book as totalBook " +
            "FROM client " +
            "c " +
            "inner join book b ON c.id = b.user_id")
    LiveData<List<ClientDataModel>> getAllWithBook();
}
```
```
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.durbinlabs.roomdemo.model.Book;

import java.util.List;

/**
 * Created by hp on 12/28/2017.
 */

@Dao
public interface BookDao {

    @Query("SELECT * FROM book")
    List<Book> getAll();

    @Query("SELECT * FROM book where user_id = :userId")
    Book getAllById(int userId);

    @Query("SELECT total_book FROM book where user_id = :userId")
    int getTotalBookById(int userId);


    @Insert
    void insert(Book... books);

}
```

নিচের কোড ব্যবহার করে আমরা ডাটাবেজের ইন্সটেন্স পেতে পারি। এটি অবশ্যই সিংগ্লেটন প্যাটার্ন হতে হবে। 

ডাটা এক্সেস করার জন্যে DAO মেথোড। 

```
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

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
```

```
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.durbinlabs.roomdemo.R;
import com.durbinlabs.roomdemo.database.AppDatabase;
import com.durbinlabs.roomdemo.model.Book;
import com.durbinlabs.roomdemo.model.Client;
import com.durbinlabs.roomdemo.model.ClientDataModel;

import java.util.List;

/**
 * Created by Shaon on 12/27/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();
    private List<ClientDataModel> modelList;
    private Context context;
    private AppDatabase db;
    private View.OnLongClickListener listener;

    public RecyclerViewAdapter(List<ClientDataModel> modelList, Context context, View.OnLongClickListener
            listener) {
        this.modelList = modelList;
        this.context = context;
        db = AppDatabase.getInstance(context);
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_row, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        int pos = holder.getAdapterPosition();
        ClientDataModel model = modelList.get(pos);
        Book book = new Book(model.getTotalBook());
        Client client = new Client(model.getId(), model.getName(), model.getAge(), book);

        holder.tvId.setText("ID: " + client.getId());
        holder.tvName.setText("Name: " + client.getName());
        holder.tvAge.setText("Age: " + client.getAge());
        holder.tvBookNo.setText(context.getString(R.string.total_book_) + " " + client.getBook().getTotalBook() + "");

        holder.itemView.setTag(client);
        holder.itemView.setOnLongClickListener(listener);
    }


    @Override
    public int getItemCount() {
        return modelList.size();
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvAge, tvBookNo, tvId;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tvID);
            tvName = itemView.findViewById(R.id.tvName);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvBookNo = itemView.findViewById(R.id.tvBookNo);
        }
    }

    public void addDataToDataModel(List<ClientDataModel> models) {
        this.modelList = models;
        notifyDataSetChanged();
    }
}
```
## VIEWMODEL:  

এটি ডাটা লোড করার জন্যে ডাটা প্রস্তুত করে এবং ডাটা ম্যানেজ করে।  একটিভিটি/ ফ্রেগমেন্টের কমিউনিকেশন স্থাপন করে সম্পূর্ণ এপের সাথে।  

ডাটা লোড এবং ম্যানেজ করার লজিক গুলো এখানে লিখতে হবে। যেহেতু ডাটাবেজ এক্সেজ মেইন থ্রেডে সম্ভব নয়, তাই এখানে এসিনটাস্ক ব্যবহার করেছি। 

```
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.durbinlabs.roomdemo.database.AppDatabase;
import com.durbinlabs.roomdemo.model.Client;
import com.durbinlabs.roomdemo.model.ClientDataModel;

import java.util.List;

/**
 * Created by Shaon on 12/30/2017.
 */

public class ClientDataViewModel extends AndroidViewModel {
    private final LiveData<List<ClientDataModel>> clientList;
    private AppDatabase db;


    public ClientDataViewModel(@NonNull Application application) {
        super(application);

        db = AppDatabase.getInstance(this.getApplication());
        clientList = db.clientDao().getAllWithBook();
    }

    public LiveData<List<ClientDataModel>> getClientList() {
        return clientList;
    }

    public void deleteClient(Client client) {
        new SingleDeletionAsyncTask(db).execute(client);
    }

    private static class SingleDeletionAsyncTask extends AsyncTask<Client, Void, Void> {
        private AppDatabase db;

        SingleDeletionAsyncTask(AppDatabase database) {
            db = database;
        }

        @Override
        protected Void doInBackground(Client... clients) {
            db.clientDao().delete(clients[0]);
            return null;
        }
    }

    public void deleteAll() {
        new AllDeletionAsyncTask(db).execute();
    }

    private static class AllDeletionAsyncTask extends AsyncTask<Void, Void, Void> {
        private AppDatabase db;

        AllDeletionAsyncTask(AppDatabase database) {
            db = database;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db.clientDao().removeAllClients();
            return null;
        }
    }
}
```



ডাটা ইন্সার্ট করার জন্যে থ্রেড ব্যবহার করতে হয়েছে। যেহেতু ROOM মেইন থ্রেডে ডাটাবেজ এক্সেস রেকোমমেন্ড করেনা বা সাপোর্ট করেনা বলা যায়। 

```
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.durbinlabs.roomdemo.R;
import com.durbinlabs.roomdemo.adapters.RecyclerViewAdapter;
import com.durbinlabs.roomdemo.database.AppDatabase;
import com.durbinlabs.roomdemo.model.Book;
import com.durbinlabs.roomdemo.model.Client;
import com.durbinlabs.roomdemo.model.ClientDataModel;
import com.durbinlabs.roomdemo.viewmodels.ClientVM;

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
    private List<ClientDataModel> modelList;
    private ClientDataViewModel clientViewModel;

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
        adapter = new RecyclerViewAdapter(new ArrayList<ClientDataModel>(), this, this);
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

        clientViewModel = ViewModelProviders.of(MainActivity.this).get(ClientDataViewModel.class);
        clientViewModel.getClientList().observe(MainActivity.this, new Observer<List<ClientDataModel>>() {
            @Override
            public void onChanged(@Nullable List<ClientDataModel> models) {
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
```
activity_main.xml
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_marginLeft="8dp"
    android:layout_marginRight="8dp"
    android:layout_marginTop="8dp"
    android:orientation="vertical"
    tools:context="com.durbinlabs.roomdemo.activities.MainActivity">

    <EditText
        android:id="@+id/evAddName"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="@string/name"
        android:paddingLeft="16dp" />

    <EditText
        android:id="@+id/evAddAge"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:hint="@string/age"
        android:inputType="number"
        android:paddingLeft="16dp" />

    <EditText
        android:id="@+id/evAddTotalBookCount"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:hint="@string/total_book"
        android:inputType="number"
        android:paddingLeft="16dp" />

    <Button
        android:id="@+id/btnAdd"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_marginTop="16dp"
        android:text="@string/add" />

    <Button
        android:id="@+id/btnRemoveAll"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:layout_marginTop="8dp"
        android:text="@string/remove_all_data" />

    <android.support.v7.widget.RecyclerView
        android:id="@+id/rv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginTop="16dp"></android.support.v7.widget.RecyclerView>

</LinearLayout>
```
recyclerview_row.xml
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
        android:id="@+id/tvID"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:paddingLeft="16dp"
        android:paddingStart="16dp"
        android:text="id"
        android:textSize="16sp"
        android:textStyle="bold" />

    <TextView
        android:id="@+id/tvName"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:paddingLeft="16dp"
        android:paddingStart="16dp"
        android:text="name"
        android:textSize="16sp"
        android:textStyle="bold" />

    <TextView
        android:id="@+id/tvAge"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:paddingLeft="16dp"
        android:paddingStart="16dp"
        android:text="25"
        android:textSize="13sp" />

    <TextView

        android:id="@+id/tvBookNo"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:paddingBottom="16dp"
        android:paddingLeft="16dp"
        android:paddingStart="16dp"
        android:text="@string/book_no_5"
        android:textSize="14sp" />
</LinearLayout>
```

### Reference:

[https://developer.android.com/training/data-storage/room/index.html]
