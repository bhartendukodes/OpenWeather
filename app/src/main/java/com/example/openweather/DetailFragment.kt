package com.example.openweather

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.openweather.adapter.WeekAdapter
import com.example.openweather.databinding.FragmentDetailBinding
import com.example.openweather.model.SpecificLocation
import com.example.openweather.utils.ApiImage
import com.example.openweather.utils.ApiText
import com.example.openweather.viewmodel.HomeViewModel

class DetailFragment : Fragment(R.layout.fragment_detail)
{
    private val binding by lazy {
        FragmentDetailBinding.inflate(layoutInflater)
    }

    private val homeViewModel by lazy {
        ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    private val weekAdapter by lazy {
        WeekAdapter()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getActivity()?.getWindow()?.setStatusBarColor(requireActivity().getColor(R.color.secondFragment))
        observe()
        init()
        onClick()
        return binding.root
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
        }
    }

    private fun init() {
        binding.apply {
            recView.layoutManager=LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            recView.adapter=weekAdapter
        }
    }

    private fun observe() {
       homeViewModel.specificWeather.observe(viewLifecycleOwner)
       {
           initUi(it)
       }

        homeViewModel.weekForecast.observe(viewLifecycleOwner)
        {
            weekAdapter.submitList(it.daily)
        }
    }

    private fun initUi(it: SpecificLocation?) {
        binding.tvCityName.text= it!!.name

        val imgId = it!!.weather[0].icon
        binding.ivConditional.setImageResource(ApiImage.weatherIcon[imgId]!!)

//        binding.tvCondition.setText(it.weather[0].description)

        val textValue = it.weather[0].description
        binding.tvCondition.setText(ApiText.ImageText[textValue]!!)
    }



}