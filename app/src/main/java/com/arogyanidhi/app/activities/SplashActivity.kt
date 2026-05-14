package com.arogyanidhi.app.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.arogyanidhi.app.R
import com.arogyanidhi.app.databinding.ActivitySplashBinding
import com.arogyanidhi.app.utils.PrefsManager

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Play pop animation on logo
        val anim = AnimationUtils.loadAnimation(this, R.anim.scale_pop)
        binding.logoCard.startAnimation(anim)

        // Navigate after 2 seconds
        Handler(Looper.getMainLooper()).postDelayed({
            val prefs = PrefsManager(this)
            val target = when {
                !prefs.isOnboardingDone() -> OnboardingActivity::class.java
                !prefs.isLoggedIn() && !prefs.isGuest() -> LoginActivity::class.java
                else -> MainActivity::class.java
            }
            startActivity(Intent(this, target))
            overridePendingTransition(R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }, 2000)
    }
}
