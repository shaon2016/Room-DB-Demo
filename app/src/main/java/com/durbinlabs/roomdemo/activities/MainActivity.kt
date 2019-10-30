package com.durbinlabs.roomdemo.activities


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.durbinlabs.roomdemo.R
import com.durbinlabs.roomdemo.adapters.RecyclerViewAdapter
import com.durbinlabs.roomdemo.database.AppDatabase
import com.durbinlabs.roomdemo.model.Book
import com.durbinlabs.roomdemo.model.Client
import com.durbinlabs.roomdemo.model.ClientDataModel
import com.durbinlabs.roomdemo.viewmodels.ClientDataViewModel

import java.util.ArrayList

class MainActivity : AppCompatActivity(), View.OnLongClickListener {
    private var db: AppDatabase? = null
    private var clients: List<Client>? = null
    private var books: List<Book>? = null
    private var rv: RecyclerView? = null
    private var btnAdd: Button? = null
    private var btnRemoveAll: Button? = null
    private var evName: EditText? = null
    private var evAge: EditText? = null
    private var evTotalBook: EditText? = null
    private var adapter: RecyclerViewAdapter? = null
    private val modelList: List<ClientDataModel>? = null
    private var clientViewModel: ClientDataViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv = findViewById(R.id.rv)
        rv!!.layoutManager = LinearLayoutManager(this@MainActivity)

        // Getting instance of our DB
        db = AppDatabase.getInstance(applicationContext)
        // Inserting data to the tables
        clients = ArrayList()
        books = ArrayList()
        adapter = RecyclerViewAdapter(ArrayList(), this, this)
        rv!!.adapter = adapter
        //insert();

        evName = findViewById(R.id.evAddName)
        evAge = findViewById(R.id.evAddAge)
        evTotalBook = findViewById(R.id.evAddTotalBookCount)
        btnAdd = findViewById(R.id.btnAdd)
        btnRemoveAll = findViewById(R.id.btnRemoveAll)

        btnAdd!!.setOnClickListener { addNewData() }

        btnRemoveAll!!.setOnClickListener { clearRecyclerView() }

        clientViewModel =
            ViewModelProviders.of(this@MainActivity).get(ClientDataViewModel::class.java)
        clientViewModel!!.clientList.observe(
            this@MainActivity,
            Observer { models -> adapter!!.addDataToDataModel(models!!) })
    }

    private fun addNewData() {
        val name = evName!!.text.toString()
        val age = parseInt(evAge!!.text.toString(), -1)
        val totalBook = parseInt(evTotalBook!!.text.toString(), -1)

        val valid = validateInput(name, age, totalBook)
        if (!valid) return
        val client = Client(name, age)

        /*
        first inserting the client.
        second getting the client id
        then inserting book number using that id
         */
        Thread(Runnable {
            db!!.clientDao().insert(client)
            val lastClient = db!!.clientDao().lastRow
            val book = Book("The Alchemist", totalBook, lastClient.id)
            db!!.bookDao().insert(book)
            // Data is being inserted in the background. That time data may not be got by the
            // asynctask
        }).start()
    }

    private fun validateInput(name: String, age: Int, totalBook: Int): Boolean {
        if (name == "") {
            evName!!.error = "Name cant be empty"
            return false
        }
        if (age < 0) {
            evAge!!.error = "Age cant be empty. It must be a numeric value"
            return false
        }
        if (totalBook < 0) {
            evAge!!.error = "Total book cant be empty. It must be a numeric value"
            return false
        }

        return true
    }


    override fun onLongClick(view: View): Boolean {
        val client = view.tag as Client
        clientViewModel!!.deleteClient(client)
        return true
    }

    private fun clearRecyclerView() {
        clientViewModel!!.deleteAll()
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName

        fun parseInt(numStr: String, defValue: Int): Int {
            try {
                return Integer.parseInt(numStr)
            } catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
                return defValue
            }

        }
    }

}
