package com.azis.azisuas

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class NetworkingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_networking)

        val btnFetch: Button = findViewById(R.id.btnFetch)
        val textView: TextView = findViewById(R.id.textView)

        btnFetch.setOnClickListener {
            fetchData(textView, "https://jsonplaceholder.typicode.com/posts/1")
        }
    }

    private fun fetchData(textView: TextView, urlString: String) {
        CoroutineScope(Dispatchers.IO).launch {
            var result: String
            try {
                val url = URL(urlString)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val responseCode = connection.responseCode
                result = if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    reader.readText().also { reader.close() }
                } else {
                    "Error: $responseCode"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                result = "Exception: ${e.message}"
            }

            withContext(Dispatchers.Main) {
                textView.text = result
            }
        }
    }
}
