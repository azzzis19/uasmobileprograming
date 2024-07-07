package com.azis.azisuas


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnListView: Button = findViewById(R.id.btnListView)
        val btnPreferences: Button = findViewById(R.id.btnPreferences)
        val btnToast: Button = findViewById(R.id.btnToast)
        val btnAlertDialog: Button = findViewById(R.id.btnAlertDialog)
        val btnNetworking: Button = findViewById(R.id.btnNetworking)
        val btnCamera: Button = findViewById(R.id.btnCamera)
        val btnApi: Button = findViewById(R.id.btnApi)
        val btnDatabase: Button = findViewById(R.id.btnDatabase)

        btnListView.setOnClickListener {
            startActivity(Intent(this, ListViewActivity::class.java))
        }

        btnPreferences.setOnClickListener {
            startActivity(Intent(this, PreferencesActivity::class.java))
        }

        btnToast.setOnClickListener {
            Toast.makeText(this, "Selamat Datang Azis!!", Toast.LENGTH_SHORT).show()
        }

        btnAlertDialog.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Alert Dialog")
                .setMessage("Ini Merupakan Menu Alert Dialog!")
                .setPositiveButton("OK", null)
                .show()
        }

        btnNetworking.setOnClickListener {
            startActivity(Intent(this, NetworkingActivity::class.java))
        }

        btnCamera.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }
        btnApi.setOnClickListener {
            startActivity(Intent(this, ApiActivity::class.java))
        }
        btnDatabase.setOnClickListener {
            startActivity(Intent(this, DatabaseActivity::class.java))
        }
    }
}