package com.example.openweather

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.openweather.adapter.ForecastAdapter
import com.example.openweather.databinding.FragmentHomeBinding
import com.example.openweather.model.SpecificLocation
import com.example.openweather.utils.ApiImage
import com.example.openweather.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment(R.layout.fragment_home)
{
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private val homeViewModel:HomeViewModel by lazy {
        ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    private val forecastAdapter by lazy {
        ForecastAdapter()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        WindowInsetsControllerCompat(requireActivity().window,requireActivity().window.decorView).isAppearanceLightStatusBars=true
        requireActivity().window.statusBarColor=Color.WHITE
        observe()
        setAdapter()
        onClick()
        return binding.root
    }

    private fun onClick() {
        binding.tvCityName.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment)
        }
    }

    private fun setAdapter() {
        binding.recView.adapter=forecastAdapter
        binding.recView.layoutManager= LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun observe() {
        homeViewModel.specificWeather.observe(viewLifecycleOwner)
        {
            initUi(it)
        }

        homeViewModel.weekForecast.observe(viewLifecycleOwner)
        {
            forecastAdapter.submitList(it.daily)
            Log.e("Tag", "${it}")
        }
    }

    private fun initUi(it: SpecificLocation) {
        binding.apply {
            tvCityName.text=it.name
            tvDescription.text=it.weather[0].main
            tvTemperature.setText((it.main.temp-273.15).toInt().toString()+"\u2103")
            tvWindSpeed.setText(it.wind.speed.toString()+ "km/h")
            ivHumidity.text=it.main.humidity.toString()+"%"

            val imgId = it.weather[0].icon

            ApiImage.weatherIcon[imgId]?.let {  imgResId->
                ivConditional.load(imgResId)

                val bitmap= BitmapFactory.decodeResource(resources,imgResId);

                setImage(bitmap)
            }
        }
    }

    private fun setImage(bitmap: Bitmap){
        Palette.from(bitmap)
            .generate(object : Palette.PaletteAsyncListener{
                override fun onGenerated(palette: Palette?) {
                    val lightSwatch = palette?.lightVibrantSwatch
                    val darkSwatch=palette?.darkVibrantSwatch
                    val dominantSwatch = palette?.dominantSwatch

                    if (lightSwatch != null) {
                        val gradientBgColor = GradientDrawable(
                            GradientDrawable.Orientation.BOTTOM_TOP,
                            intArrayOf(lightSwatch.rgb,Color.WHITE)
                        )

                        binding.root.background = gradientBgColor
                    }
                }
            })
    }

}