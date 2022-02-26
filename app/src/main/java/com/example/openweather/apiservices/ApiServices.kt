package com.example.openweather.apiservices

import com.example.openweather.model.DailyForecast
import com.example.openweather.model.SpecificLocation
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("/data/2.5/weather")
    suspend fun getSpecificLocationDetails(
        @Query("lat")lat:Double,
        @Query("lon")lon:Double,
        @Query("appid")apiKey:String
    ):SpecificLocation

    @GET("data/2.5/onecall")
    suspend fun getFullWeekForecast(
        @Query("lat")lat:Double,
        @Query("lon")lon: Double,
        @Query("exclude")exclude:String,
        @Query("appid")apiKey: String
    ):DailyForecast
}