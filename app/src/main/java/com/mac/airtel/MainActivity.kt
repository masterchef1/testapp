package com.mac.airtel

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var animation: Animation
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val image = findViewById<ImageView>(R.id.splash)
        animation = AnimationUtils.loadAnimation(this, R.anim.zoom)

        image.animation = animation

        Handler().postDelayed(Runnable {
            startActivity(Intent(this, HomeScreen::class.java))
            overridePendingTransition(0, 0)
        }, 1500)
    }
}