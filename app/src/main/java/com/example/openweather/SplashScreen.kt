package com.example.openweather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()

        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            withContext(Dispatchers.Main){
                var intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
    }
}