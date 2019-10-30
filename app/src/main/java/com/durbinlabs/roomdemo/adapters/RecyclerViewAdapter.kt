package com.durbinlabs.roomdemo.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.durbinlabs.roomdemo.R
import com.durbinlabs.roomdemo.model.Client

/**
 * Created by hp on 12/27/2017.
 */

class RecyclerViewAdapter(
    private var clients: ArrayList<Client>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.recycler_row, null)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pos = holder.adapterPosition
        val model = clients[pos]

        holder.tvId.text = "ID: " + model.id
        holder.tvName.text = "Name: " + model.name!!
        holder.tvAge.text = "Age: " + model.age

        holder.itemView.tag = model

        Log.d("DATATAG", model.toString())
    }


    override fun getItemCount(): Int {
        return clients!!.size
    }

    fun addAll(it: List<Client>) {
        clients.clear()
        clients.addAll(it)
        notifyDataSetChanged()
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tvName)
        var tvAge: TextView = itemView.findViewById(R.id.tvAge)
        var tvId: TextView = itemView.findViewById(R.id.tvID)

    }

    companion object {
        private val TAG = RecyclerViewAdapter::class.java.simpleName
    }
}
