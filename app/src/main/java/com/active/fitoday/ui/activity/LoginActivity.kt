package com.active.fitoday.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.active.fitoday.R
import com.active.fitoday.databinding.ActivityLoginBinding
import com.active.fitoday.databinding.LayoutIntroViewPagerFragmentBinding

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    var isForgetPasswordEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
        }
    }
}