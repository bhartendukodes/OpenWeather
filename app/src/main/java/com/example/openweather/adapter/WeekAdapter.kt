package com.example.openweather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.openweather.databinding.LayoutWeekwiseBinding
import com.example.openweather.model.DailyForecast
import com.example.openweather.utils.ApiImage
import com.example.openweather.utils.ApiText
import java.lang.reflect.Array.get
import java.text.SimpleDateFormat
import java.util.*

class WeekAdapter():ListAdapter<DailyForecast.Daily, WeekAdapter.viewHolder>(DiffUtilss) {

    private val calender= Calendar.getInstance()

    object DiffUtilss : DiffUtil.ItemCallback<DailyForecast.Daily>() {
        override fun areItemsTheSame(
            oldItem: DailyForecast.Daily,
            newItem: DailyForecast.Daily
        ): Boolean {
            return oldItem.dt == newItem.dt
        }

        override fun areContentsTheSame(
            oldItem: DailyForecast.Daily,
            newItem: DailyForecast.Daily
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class viewHolder(val binding: LayoutWeekwiseBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(
            LayoutWeekwiseBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val data = getItem(position)

        holder.binding.apply {
//            tvHigh.setText((position.weather[0].main).toInt().toString()+"\u2103")
            tvHigh.text = (data.temp.min!! - 273.15).toInt().toString() + "\u2103"
            tvLow.text = (data.temp.max!! - 273.15).toInt().toString() + "\u2103"

            val imgId = data.weather[0].icon
            ivConditional.setImageResource(ApiImage.weatherIcon[imgId]!!)
            dateView.text = getTime(position)

        }
    }

    private fun getTime(position: Int): CharSequence? {
        val dayOfWeek = calender.get(Calendar.DAY_OF_WEEK) + position

        val currentDay = dayOfWeek

        var day = currentDay
        if (day > 7) {
            day = day % 7
        }
        return when (day) {
            2 -> ("Monday")
            3 -> ("Tuesday")
            4 ->("Wednesday")
            5 -> ("Thrusday")
            6 ->("Friday")
            7 ->("Saturday")
            1 ->("Sunday")
            else -> ""
        }


    }
}
