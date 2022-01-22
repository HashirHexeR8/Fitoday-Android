package com.active.fitoday.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.active.fitoday.R
import com.active.fitoday.databinding.ActivityIntroLoginBinding
import com.active.fitoday.ui.adapter.IntroViewPagerAdapter
import me.relex.circleindicator.CircleIndicator3

class IntroLoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityIntroLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Fitoday_NoActionBar)
        super.onCreate(savedInstanceState)
        binding = ActivityIntroLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val adapter = IntroViewPagerAdapter(this@IntroLoginActivity)
        binding.vpIntro.adapter = adapter
        binding.pageIndicator.setViewPager(binding.vpIntro)
        binding.pageIndicator.createIndicators(3,0)
        adapter.registerAdapterDataObserver(binding.pageIndicator.adapterDataObserver)

        binding.rlBtnLoginFacebook.setOnClickListener {
            val intent: Intent = Intent (this@IntroLoginActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.rlBtnLoginGoogle.setOnClickListener {
            val intent: Intent = Intent (this@IntroLoginActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.rlBtnLoginEmail .setOnClickListener {
            val intent: Intent = Intent (this@IntroLoginActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.rlBtnLoginFitoday .setOnClickListener {
            val intent: Intent = Intent (this@IntroLoginActivity, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}