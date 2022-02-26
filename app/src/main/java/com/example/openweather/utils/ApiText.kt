package com.example.openweather.utils

import com.example.openweather.R

object ApiText {

public val ImageText = mapOf(
    Pair("clear sky", R.string.clear_sky),
    Pair("few clouds", R.string.few_clouds),
    Pair("scattered clouds", R.string.scattered_clouds),
    Pair("broken clouds", R.string.broken_clouds),
    Pair("shower rain", R.string.shower_rain),
    Pair("rain", R.string.rain),
    Pair("thunderstorm", R.string.thunderstorm),
    Pair("snow", R.string.snow),
    Pair("mist", R.string.mist)
)

    public val daysCount = mapOf(
        Pair(1, "Monday"),
        Pair(2, "Tuesday"),
        Pair(3, "Wednesday"),
        Pair(4, "Thursday"),
        Pair(5, "Friday"),
        Pair(6, "Saturday"),
        Pair(7, "Sunday")
    )
}