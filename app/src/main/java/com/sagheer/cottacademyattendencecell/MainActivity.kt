package com.sagheer.cottacademyattendencecell

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        removeLogoAfter3Sec()


        //btn_Login()
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
