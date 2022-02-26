package com.example.openweather.repository

import com.example.openweather.model.DailyForecast
import com.example.openweather.model.SpecificLocation

object Repository {

    private val apiServices = Network.getApiServices()


    suspend fun getSpecificLocationDetails(lat:Double, lon:Double):SpecificLocation
    {
        return apiServices.getSpecificLocationDetails(lat, lon, Network.API_KEY)
    }

    suspend fun getFullWeekForecast(lat: Double, lon: Double):DailyForecast
    {
        return apiServices.getFullWeekForecast(lat, lon, "current" , Network.API_KEY)
    }
}