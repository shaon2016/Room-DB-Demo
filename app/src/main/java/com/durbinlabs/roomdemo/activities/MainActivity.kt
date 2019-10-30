package com.durbinlabs.roomdemo.activities


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.durbinlabs.roomdemo.Injection

import com.durbinlabs.roomdemo.R
import com.durbinlabs.roomdemo.adapters.RecyclerViewAdapter
import com.durbinlabs.roomdemo.model.Client
import com.durbinlabs.roomdemo.viewmodels.ClientVM
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var clientVM: ClientVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModelFactory = Injection.provideViewModelFactory(this)
        clientVM = ViewModelProviders.of(this, viewModelFactory)[ClientVM::class.java]


        btnAdd.setOnClickListener {
            val client = Client()
            client.name = evAddName.text.toString()
            client.age = evAddAge.text.toString().toInt()
            clientVM.insert(client)
        }

        rv.layoutManager = LinearLayoutManager(this)
        val adapter = RecyclerViewAdapter(ArrayList(), this)
        rv.adapter = adapter

        clientVM.clients
            .observe(this, Observer {
            adapter.addAll(it)
        })

    }


}
