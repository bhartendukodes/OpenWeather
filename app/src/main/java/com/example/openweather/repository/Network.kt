package com.example.openweather.repository

import com.example.openweather.apiservices.ApiServices
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Network {

     const val API_KEY="3dd62ffb1d40672734d80bd5253c4ce1"
    private val BASE_URL="https://api.openweathermap.org"


    fun getApiServices():ApiServices{

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiServices::class.java)
    }
}