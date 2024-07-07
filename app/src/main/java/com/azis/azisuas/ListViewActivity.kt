package com.azis.azisuas

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ListViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview)

        val listView: ListView = findViewById(R.id.listView)
        val items = listOf("Mobile Programming", "Pemrograman Web", "Sistem Informasi Manajemen", "Kecerdasan Buatan")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)

        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = items[position]
            Toast.makeText(this, "Clicked: $selectedItem", Toast.LENGTH_SHORT).show()
        }
    }
}
