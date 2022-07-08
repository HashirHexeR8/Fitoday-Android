package com.active.fitody.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.active.fitody.ServiceLocator
import com.active.fitody.UserPrefs
import com.active.fitody.databinding.ActivityLoginBinding
import com.active.fitody.util.Constants
import com.active.fitody.util.Utilities

class LoginActivity: AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private var isForgetPasswordEnabled = false
    private var isJoinFitoday = false
    private val userManagementService = ServiceLocator.getUserManagementService()

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
                if (checkForValidation()) {
                    val intent = Intent (this@LoginActivity, UserSignUpActivity::class.java)
                    intent.putExtra(Constants.userEmailExtra, binding.edEmail.text.toString())
                    intent.putExtra(Constants.userPasswordExtra, binding.edPassword.text.toString())
                    startActivity(intent)
                }
            }
            else {
                if (checkForValidation()) {
                    if (userManagementService.getUserInfo(binding.edEmail.text.toString(), binding.edPassword.text.toString())) {
                        UserPrefs.getInstance().putUserLoggedIn(true)
                        val intent = Intent (this@LoginActivity, HomeDashboardActivity::class.java)
                        startActivity(intent)
                    }
                    else {
                        Utilities.instance.showSnackBar(binding.root, "User does not exist.")
                    }
                }
            }
        }
    }

    fun checkForValidation(): Boolean {
        //First Name Text field
        if (binding.edEmail.text.toString().isEmpty()) {
            Utilities.instance.showSnackBar(binding.root, "Please enter your email")
            return false
        }

        //Last Name Text field
        if (binding.edPassword.text.toString().isEmpty()) {
            Utilities.instance.showSnackBar(binding.root, "Please enter your password")
            return false
        }

        if (!isJoinFitoday) {
            return true
        }

        //Height text view check
        if (!binding.cbPolicy.isChecked) {
            Utilities.instance.showSnackBar(binding.root, "Please check policy Box")
            return false
        }

        //Weight text view check
        if (!binding.cbUpdatesPolicy.isChecked) {
            Utilities.instance.showSnackBar(binding.root, "Please check update policy box")
            return false
        }
        return true
    }
}