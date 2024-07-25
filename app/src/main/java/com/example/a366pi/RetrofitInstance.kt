package com.example.a366pi

// Gson packages
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Json Handler
object RetrofitInstance {
    private const val BASE_URL = "https://reqres.in/api/"

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}