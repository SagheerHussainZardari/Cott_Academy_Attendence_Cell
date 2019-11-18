package com.sagheer.cottacademyattendencecell

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.title = "Attendence Cell"
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"))

        setSupportActionBar(toolbar)

        toolbar.visibility  = View.GONE

        Handler().postDelayed({
            //iv_splashLogo.animate().translationXBy(-1000f).duration = 5000
            iv_splashLogo.animate().translationYBy(-5000f).duration = 2500
            toolbar.visibility  = View.VISIBLE
        }, 3000)

    }

}
