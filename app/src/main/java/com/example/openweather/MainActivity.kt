package com.example.openweather

import com.example.openweather.databinding.ActivityMainBinding

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.openweather.viewmodel.HomeViewModel
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val PERMISSION_ID = 69
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
    }

    private fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.lastLocation.addOnCompleteListener(object :
                    OnCompleteListener<Location> {
                    override fun onComplete(p0: Task<Location>) {
                        val location = p0.getResult();
                        if (location == null)
                            requestNewLocationData()
                        else {
                            viewModel.getSpecificLocationDetails(
                                location.latitude,
                                location.longitude
                            )
                            viewModel.getFullWeekForecast(
                                location.latitude,
                                location.longitude
                            )
                        }
                    }

                })
            } else {
                Toast.makeText(this, "Please turn on your location...", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermission()
        }
    }

    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5
            fastestInterval = 0
            numUpdates = 1
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (checkPermission())
            mFusedLocationClient.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                Looper.myLooper()!!
            )

    }


    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location = locationResult.lastLocation
            viewModel.getSpecificLocationDetails(
                mLastLocation.latitude,
                mLastLocation.longitude
            )
            viewModel.getFullWeekForecast(mLastLocation.latitude, mLastLocation.longitude)
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED;
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_ID) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                getLastLocation()
        }
    }

    override fun onResume() {
        super.onResume()
        if (checkPermission())
            getLastLocation()
    }

}