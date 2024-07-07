package com.azis.azisuas


import android.content.ContentValues
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DatabaseActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var etData: EditText
    private lateinit var tvData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database)

        databaseHelper = DatabaseHelper(this)
        etData = findViewById(R.id.etData)
        tvData = findViewById(R.id.tvData)

        val btnSave: Button = findViewById(R.id.btnSave)
        val btnRetrieve: Button = findViewById(R.id.btnRetrieve)

        btnSave.setOnClickListener {
            val data = etData.text.toString()
            if (data.isNotEmpty()) {
                saveData(data)
                etData.text.clear()
            } else {
                Toast.makeText(this, "Please enter data", Toast.LENGTH_SHORT).show()
            }
        }

        btnRetrieve.setOnClickListener {
            retrieveData()
        }
    }

    private fun saveData(data: String) {
        val db = databaseHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_DATA, data)
        }
        val newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values)
        if (newRowId != -1L) {
            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Error saving data", Toast.LENGTH_SHORT).show()
        }
    }

    private fun retrieveData() {
        val db = databaseHelper.readableDatabase
        val projection = arrayOf(DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_DATA)
        val cursor = db.query(
            DatabaseHelper.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            null
        )
        with(cursor) {
            if (moveToFirst()) {
                val dataList = mutableListOf<String>()
                do {
                    val data = getString(getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATA))
                    dataList.add(data)
                } while (moveToNext())
                tvData.text = dataList.joinToString("\n")
            } else {
                tvData.text = "No data found"
            }
        }
        cursor.close()
    }
}