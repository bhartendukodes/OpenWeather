package com.example.openweather.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyForecast(
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
):Parcelable
{
    @Parcelize
    data class Daily(
        val clouds: Int,
        val dew_point: Double,
        val dt: Int,
        val temp:Temp,
        val humidity: Int,
        val moon_phase: Double,
        val moonrise: Int,
        val moonset: Int,
        val pop: Float,
        val main:Main,
        val pressure: Int,
        val rain: Double,
        val sunrise: Int,
        val sunset: Int,
        val uvi: Double,
        val weather: List<Weather>,
        val wind_deg: Int,
        val wind_gust: Double,
        val wind_speed: Double
    ):Parcelable


    @Parcelize
    data class Weather(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
    ):Parcelable


    @Parcelize
    data class Main(
        val feels_like: Double,
        val grnd_level: Int,
        val humidity: Int,
        val pressure: Int,
        val sea_level: Int,
        val temp: Double,
        val temp_max: Double,
        val temp_min: Double
    ):Parcelable

    @Parcelize
    data class Temp(
        val max: Double?,
        val min: Double?
    ):Parcelable
}