package com.example.openweather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.openweather.databinding.TemperatureLayoutBinding
import com.example.openweather.model.DailyForecast
import com.example.openweather.utils.ApiImage
import java.util.*

class ForecastAdapter():ListAdapter<DailyForecast.Daily, ForecastAdapter.ViewHolder>(DiffUtils) {

    private val calendar=Calendar.getInstance()

    object DiffUtils:DiffUtil.ItemCallback<DailyForecast.Daily>()
    {
        override fun areItemsTheSame(oldItem: DailyForecast.Daily, newItem: DailyForecast.Daily): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(oldItem: DailyForecast.Daily, newItem: DailyForecast.Daily): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(val binding: TemperatureLayoutBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TemperatureLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather = getItem(position)

        holder.binding.apply {
            val imgId = weather.weather[0].icon
            ivWeatherImg.setImageResource(ApiImage.weatherIcon[imgId]!!)
            tvTemperature.setText((weather.temp.max!!-273.15).toInt().toString()+"\u2103")
            tvTime.text=getTime(position)
            }
        }

    private fun getTime(weather: Int): CharSequence? {
        var am_pm=""

        var time=calendar.get(Calendar.HOUR_OF_DAY) + weather

        if (time>=24){
            time =time - 24;
        }

        if (time==0){
            time=12
            am_pm="AM"
        }else if (time>=12 ){
            am_pm=" PM"
            if(time>24){
                time=time-24
            }
            if(time>=12){
                time=time-12
            }
            if (time==0)
                time=12
        }else{
            am_pm=" AM"
        }

        return time.toString()+am_pm
    }

}