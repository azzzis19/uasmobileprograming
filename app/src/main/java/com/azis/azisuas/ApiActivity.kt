package com.azis.azisuas

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ApiActivity : AppCompatActivity() {

    private lateinit var tvApiData: TextView
    private val baseUrl = "https://jsonplaceholder.typicode.com/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api)

        val btnFetchData: Button = findViewById(R.id.btnFetchData)
        val btnFetchDataCoroutine: Button = findViewById(R.id.btnFetchDataCoroutine)
        tvApiData = findViewById(R.id.tvApiData)

        btnFetchData.setOnClickListener {
            fetchDataWithRetrofit()
        }

        btnFetchDataCoroutine.setOnClickListener {
            fetchDataWithCoroutine()
        }
    }

    private fun fetchDataWithRetrofit() {
        val logging = HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        val httpClient = OkHttpClient.Builder().addInterceptor(logging).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val call = apiService.getPosts()

        call.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                if (response.isSuccessful) {
                    val posts = response.body()
                    val displayText = posts?.joinToString("\n") { post -> "Title: ${post.title}" }
                    tvApiData.text = displayText
                } else {
                    tvApiData.text = "Error: ${response.code()}"
                    Toast.makeText(this@ApiActivity, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                tvApiData.text = "Failure: ${t.message}"
                Toast.makeText(this@ApiActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchDataWithCoroutine() {
        CoroutineScope(Dispatchers.IO).launch {
            var result: String
            try {
                val url = URL("https://jsonplaceholder.typicode.com/posts/1")
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
                tvApiData.text = result
            }
        }
    }

    interface ApiService {
        @GET("posts")
        fun getPosts(): Call<List<Post>>
    }

    data class Post(
        val userId: Int,
        val id: Int,
        val title: String,
        val body: String
    )
}
