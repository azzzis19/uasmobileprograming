package com.azis.azisuas

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class PreferencesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferences)

        val editText: EditText = findViewById(R.id.editText)
        val btnSave: Button = findViewById(R.id.btnSave)
        val btnLoad: Button = findViewById(R.id.btnLoad)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        btnSave.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.putString("savedText", editText.text.toString())
            editor.apply()
            Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show()
        }

        btnLoad.setOnClickListener {
            val savedText = sharedPreferences.getString("savedText", "No data saved")
            editText.setText(savedText)
            Toast.makeText(this, "Data loaded", Toast.LENGTH_SHORT).show()
        }
    }
}
