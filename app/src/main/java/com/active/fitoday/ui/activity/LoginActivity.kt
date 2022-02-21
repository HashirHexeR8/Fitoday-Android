package com.active.fitoday.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.active.fitoday.R
import com.active.fitoday.databinding.ActivityLoginBinding
import com.active.fitoday.databinding.LayoutIntroViewPagerFragmentBinding

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private var isForgetPasswordEnabled = false
    private var isJoinFitoday = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        isJoinFitoday = intent.getBooleanExtra("isJoinFitoday", false)

        if (isJoinFitoday) {
            binding.tvForgetPassword.visibility = View.GONE
            binding.llPrivacyPolicy.visibility = View.VISIBLE
            binding.llUpdatesPolicy.visibility = View.VISIBLE
            binding.btnLogin.text = "Next"
        }
        else {
            binding.tvForgetPassword.visibility = View.VISIBLE
            binding.llPrivacyPolicy.visibility = View.GONE
            binding.llUpdatesPolicy.visibility = View.GONE
            binding.btnLogin.text = "Login"
        }

        binding.tvForgetPassword.setOnClickListener {
            if (!isForgetPasswordEnabled) {
                isForgetPasswordEnabled = !isForgetPasswordEnabled
                binding.edPassword.visibility = View.GONE
                binding.tvForgetPassword.visibility = View.GONE
                binding.btnLogin.text = "Send Reset Email"
            }
        }

        binding.btnLogin.setOnClickListener {
            if (isForgetPasswordEnabled) {
                isForgetPasswordEnabled = !isForgetPasswordEnabled
                binding.edPassword.visibility = View.VISIBLE
                binding.tvForgetPassword.visibility = View.VISIBLE
                binding.btnLogin.text = "Login"
            }
            else if (isJoinFitoday) {
                val intent: Intent = Intent (this@LoginActivity, UserSignUpActivity::class.java)
                startActivity(intent)
            }
            else {
                val intent: Intent = Intent (this@LoginActivity, HomeDashboardActivity::class.java)
                startActivity(intent)
            }
        }
    }
}