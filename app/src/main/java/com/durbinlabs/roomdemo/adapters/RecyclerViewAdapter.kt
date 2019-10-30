package com.durbinlabs.roomdemo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

import com.durbinlabs.roomdemo.R
import com.durbinlabs.roomdemo.database.AppDatabase
import com.durbinlabs.roomdemo.model.Book
import com.durbinlabs.roomdemo.model.Client
import com.durbinlabs.roomdemo.model.ClientDataModel

/**
 * Created by hp on 12/27/2017.
 */

class RecyclerViewAdapter(
    private var modelList: List<ClientDataModel>?,
    private val context: Context,
    private val listener: View.OnLongClickListener
) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.recycler_row, null)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pos = holder.adapterPosition
        val model = modelList!![pos]
        val book = Book(model.totalBook)
        val client = Client(model.id, model.name!!, model.age, book)

        holder.tvId.text = "ID: " + client.id
        holder.tvName.text = "Name: " + client.name!!
        holder.tvAge.text = "Age: " + client.age
        holder.tvBookNo.text =
            context.getString(R.string.total_book_) + " " + client.book!!.totalBook + ""


        holder.itemView.tag = client
        holder.itemView.setOnLongClickListener(listener)
    }


    override fun getItemCount(): Int {
        return modelList!!.size
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView
        var tvAge: TextView
        var tvBookNo: TextView
        var tvId: TextView

        init {

            tvId = itemView.findViewById(R.id.tvID)
            tvName = itemView.findViewById(R.id.tvName)
            tvAge = itemView.findViewById(R.id.tvAge)
            tvBookNo = itemView.findViewById(R.id.tvBookNo)
        }
    }

    fun addDataToDataModel(models: List<ClientDataModel>) {
        this.modelList = models
        notifyDataSetChanged()
    }

    companion object {
        private val TAG = RecyclerViewAdapter::class.java.simpleName
    }
}
