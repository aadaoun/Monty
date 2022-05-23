package com.develop.monty

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils


@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    private lateinit var a1: TextView
    private lateinit var a2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        a1 = findViewById(R.id.a1)
        a2 = findViewById(R.id.a2)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        //starting the animation

        Handler().postDelayed({
            a1.startAnimation(animation)
            a1.visibility= View.VISIBLE

        }, 1000)
        Handler().postDelayed({
            a2.startAnimation(animation)
            a2.visibility= View.VISIBLE

        }, 1500)
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
