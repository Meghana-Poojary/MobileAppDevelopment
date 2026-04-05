package com.example.jokes
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.view.View

class MainActivity : AppCompatActivity() {

    private lateinit var tvSetup: TextView
    private lateinit var tvPunchline: TextView
    private lateinit var btnJoke: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvSetup = findViewById(R.id.tvSetup)
        tvPunchline = findViewById(R.id.tvPunchline)
        btnJoke = findViewById(R.id.btnJoke)

        btnJoke.setOnClickListener {
            fetchJoke()
        }
    }

    private fun fetchJoke() {
        val call = RetrofitClient.instance.getJoke()

        call.enqueue(object : Callback<Joke> {
            override fun onResponse(call: Call<Joke>, response: Response<Joke>) {
                if (response.isSuccessful && response.body() != null) {
                    val joke = response.body()!!

                    tvSetup.text = joke.setup
                    tvPunchline.text = joke.punchline
                    tvPunchline.visibility = View.GONE

                    tvSetup.postDelayed({
                        tvPunchline.visibility = View.VISIBLE
                    }, 2000)

                } else {
                    Toast.makeText(this@MainActivity, "Failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Joke>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}