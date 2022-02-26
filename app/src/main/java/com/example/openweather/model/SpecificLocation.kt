package com.example.openweather.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpecificLocation(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
):Parcelable {
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
    data class Coord(
        val lat: Double,
        val lon: Double
    ):Parcelable

    @Parcelize
    data class Clouds(
        val all: Int
    ):Parcelable

    @Parcelize
    data class Sys(
        val country: String,
        val id: Int,
        val sunrise: Int,
        val sunset: Int,
        val type: Int
    ):Parcelable

    @Parcelize
    data class Weather(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
    ):Parcelable

    @Parcelize
    data class Wind(
        val deg: Int,
        val gust: Double,
        val speed: Double
    ):Parcelable
}