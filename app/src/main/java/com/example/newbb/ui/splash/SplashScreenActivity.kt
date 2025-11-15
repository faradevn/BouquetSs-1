package com.example.newbb.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.newbb.R
import com.example.newbb.databinding.ActivitySplashScreenBinding
import com.example.newbb.ui.MainActivity
import com.example.newbb.ui.onboarding.OnBoardingScreenActivity
import com.google.firebase.auth.FirebaseAuth


class SplashScreenActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi binding
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser

        Handler().postDelayed({
            val signInIntent = Intent(this, OnBoardingScreenActivity::class.java)
            startActivity(signInIntent)
            finish()
            if (user != null) {
                val dashboardIntent = Intent(this, MainActivity::class.java)
                startActivity(dashboardIntent)
                finish()
            } else {
                val signInIntent = Intent(this, OnBoardingScreenActivity::class.java)
                startActivity(signInIntent)
                finish()
            }
        }, 1000)
    }
}


