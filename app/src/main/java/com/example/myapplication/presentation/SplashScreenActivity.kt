package com.example.myapplication.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.myapplication.MainActivity
import com.example.myapplication.R

class SplashScreenActivity : AppCompatActivity() {

     val ANIMATION_TIME: Long = 6000 //Change time according to your animation.


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(this.mainLooper).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()

        }, ANIMATION_TIME)

    }
}