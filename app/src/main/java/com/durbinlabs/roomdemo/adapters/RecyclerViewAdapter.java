package com.durbinlabs.roomdemo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.durbinlabs.roomdemo.R;
import com.durbinlabs.roomdemo.database.AppDatabase;
import com.durbinlabs.roomdemo.model.Book;
import com.durbinlabs.roomdemo.model.Client;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by hp on 12/27/2017.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();
    private List<Client> clients;
    private Context context;

    public RecyclerViewAdapter(List<Client> clients, Context context) {
        this.clients = clients;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recycler_row, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tvName.setText(clients.get(position).getName());
        holder.tvAge.setText(clients.get(position).getAge() + "");
    }

    @Override
    public int getItemCount() {
        return clients.size();
    }

    public void clear() {
        int itemCount = getItemCount();
        clients.clear();
        notifyItemRangeRemoved(0, itemCount);
    }

    protected class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvAge, tvBookNo;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvBookNo = itemView.findViewById(R.id.tvBookNo);
        }
    }
}
