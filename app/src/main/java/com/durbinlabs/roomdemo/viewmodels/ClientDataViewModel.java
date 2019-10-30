package com.durbinlabs.roomdemo.viewmodels;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

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
