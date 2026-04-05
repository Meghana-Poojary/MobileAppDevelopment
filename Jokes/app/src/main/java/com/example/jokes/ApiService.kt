package com.example.jokes

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("random_joke")
    fun getJoke(): Call<Joke>
}