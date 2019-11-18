package com.sagheer.cottacademyattendencecell

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.view.animation.Animation
import android.widget.Toast
import android.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        removeLogoAfter3Sec()
    }

    private fun removeLogoAfter3Sec() {
        Handler().postDelayed({
            iv_splashLogo.animate().alpha(0f).duration = 2000
            tv_developedBy.animate().alpha(0f).duration = 2000
        }, 2000)
        Handler().postDelayed({
            iv_splashLogo.visibility = View.GONE
            tv_developedBy.visibility = View.GONE
            setCustomToolBar()
        }, 4000)

    }


    private fun setCustomToolBar() {
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.title = "Attendence Cell"
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"))
        setSupportActionBar(toolbar)
        toolbar.visibility  = View.VISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu , menu)
        return true
    }


}
