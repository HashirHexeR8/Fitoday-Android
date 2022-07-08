package com.active.fitody.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.active.fitody.R
import com.active.fitody.UserPrefs

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (UserPrefs.getInstance().isUserLoggedIn()) {
            finish()
            val intent = Intent (this@MainActivity, HomeDashboardActivity::class.java)
            startActivity(intent)
        }
        else {
            finish()
            val intent = Intent (this@MainActivity, IntroLoginActivity::class.java)
            startActivity(intent)
        }
    }
}