package com.durbinlabs.roomdemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.durbinlabs.roomdemo.R;
import com.durbinlabs.roomdemo.database.AppDatabase;
import com.durbinlabs.roomdemo.model.Book;
import com.durbinlabs.roomdemo.model.Client;
import com.durbinlabs.roomdemo.model.ClientDataModel;

import java.util.List;

/**
 * Created by hp on 12/27/2017.
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
