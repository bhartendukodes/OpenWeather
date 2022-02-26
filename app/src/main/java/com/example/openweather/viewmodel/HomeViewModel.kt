package com.example.openweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openweather.model.DailyForecast
import com.example.openweather.model.SpecificLocation
import com.example.openweather.repository.Repository
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel():ViewModel() {

    val specificWeather = MutableLiveData<SpecificLocation>()
    val weekForecast = MutableLiveData<DailyForecast>()

    fun getSpecificLocationDetails(lat:Double, lon:Double)
    {
        viewModelScope.launch {
            try {
                val weather = Repository.getSpecificLocationDetails(lat, lon)
                specificWeather.postValue(weather)
            }catch (e:Exception)
            {
                e.printStackTrace()
            }
        }
    }

    fun getFullWeekForecast(lat: Double, lon: Double)
    {
        viewModelScope.launch {
            try {
                val weather = Repository.getFullWeekForecast(lat, lon)
                weekForecast.postValue(weather)
            }catch (e:Exception)
            {
                e.printStackTrace()
            }
        }
    }

}