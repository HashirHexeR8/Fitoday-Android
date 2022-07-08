package com.active.fitody.ui.activity

import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.active.fitody.R
import com.active.fitody.UserPrefs
import com.active.fitody.databinding.ActivityIntroLoginBinding
import com.active.fitody.ui.adapter.IntroViewPagerAdapter
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth

class IntroLoginActivity : AppCompatActivity() {

    private val REQ_ONE_TAP = 2  // Can be any integer unique to the Activity

    lateinit var binding: ActivityIntroLoginBinding
    private var mFirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var oneTapClient: SignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Fitoday_NoActionBar)
        if (UserPrefs.getInstance().isUserLoggedIn()) {
            finish()
        }
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
//            val intent: Intent = Intent (this@IntroLoginActivity, LoginActivity::class.java)
//            startActivity(intent)
        }

        binding.rlBtnLoginGoogle.setOnClickListener {
//            val intent: Intent = Intent (this@IntroLoginActivity, LoginActivity::class.java)
//            startActivity(intent)
            oneTapClient = Identity.getSignInClient(this)
            val signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(
                    BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(false)
                        .setFilterByAuthorizedAccounts(false)
                        .build())
                .build()

            oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(this) { result ->
                    try {
                        startIntentSenderForResult(
                            result.pendingIntent.intentSender, REQ_ONE_TAP,
                            null, 0, 0, 0, null)
                    } catch (e: IntentSender.SendIntentException) {
                        Log.e("Google Signin", "Couldn't start One Tap UI: ${e.localizedMessage}")
                    }
                }
                .addOnFailureListener(this) { e ->
                    // No saved credentials found. Launch the One Tap sign-up flow, or
                    // do nothing and continue presenting the signed-out UI.
                    Log.d("Google SigninException", e.localizedMessage)
                }


        }

        binding.rlBtnLoginEmail .setOnClickListener {
            val intent: Intent = Intent (this@IntroLoginActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.rlBtnLoginFitoday .setOnClickListener {
            val intent: Intent = Intent (this@IntroLoginActivity, LoginActivity::class.java)
            intent.putExtra("isJoinFitoday", true)
            startActivity(intent)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQ_ONE_TAP -> {
                try {
                    val credential = oneTapClient.getSignInCredentialFromIntent(data)
                    val idToken = credential.googleIdToken
                    val username = credential.id
                    val password = credential.password
                    when {
                        idToken != null -> {
                            // Got an ID token from Google. Use it to authenticate
                            // with your backend.
                            Log.d("Google Signin", "Got ID token.")
                        }
                        password != null -> {
                            // Got a saved username and password. Use them to authenticate
                            // with your backend.
                            Log.d("Google Signin", "Got password.")
                        }
                        else -> {
                            // Shouldn't happen.
                            Log.d("Google Signin", "No ID token or password!")
                        }
                    }
                } catch (e: ApiException) {
                    Log.d("Google Signin", e.message ?: "exception")
                    // ...
                }
            }
        }
    }
}