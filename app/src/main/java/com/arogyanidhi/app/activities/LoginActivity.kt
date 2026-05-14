package com.arogyanidhi.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.arogyanidhi.app.R
import com.arogyanidhi.app.databinding.ActivityLoginBinding
import com.arogyanidhi.app.utils.PrefsManager
import com.arogyanidhi.app.utils.Validator
import com.arogyanidhi.app.viewmodels.AuthViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val vm: AuthViewModel by viewModels()
    private lateinit var prefs: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = PrefsManager(this)

        binding.btnLogin.setOnClickListener { doLogin() }
        binding.btnGuest.setOnClickListener { continueAsGuest() }
        binding.tvGoSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            finish()
        }
        binding.tvForgot.setOnClickListener { handleForgot() }

        observeVm()
    }

    private fun observeVm() {
        vm.loading.observe(this) {
            binding.progress.visibility = if (it) View.VISIBLE else View.GONE
            binding.btnLogin.isEnabled = !it
        }
        vm.error.observe(this) {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                vm.clearMessages()
            }
        }
        vm.success.observe(this) {
            if (!it.isNullOrEmpty()) {
                if (it == "reset_sent") {
                    Toast.makeText(this, "Reset link sent to email", Toast.LENGTH_SHORT).show()
                } else {
                    // Logged in successfully
                    val email = binding.etId.text?.toString()?.trim().orEmpty()
                    prefs.setLoggedIn(true)
                    prefs.setGuest(false)
                    prefs.setUser(
                        name = email.substringBefore("@").ifEmpty { "User" },
                        email = email,
                        phone = ""
                    )
                    goHome()
                }
                vm.clearMessages()
            }
        }
    }

    private fun doLogin() {
        val id = binding.etId.text?.toString()?.trim().orEmpty()
        val pwd = binding.etPwd.text?.toString().orEmpty()
        if (id.isEmpty() || pwd.isEmpty()) {
            Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show()
            return
        }
        if (!Validator.isValidEmail(id)) {
            // Allow phone login via mock: in Firebase you'd use phone auth flow
            Toast.makeText(this, "Use email login (phone auth not configured)", Toast.LENGTH_SHORT).show()
            return
        }
        vm.signIn(id, pwd)
    }

    private fun handleForgot() {
        val id = binding.etId.text?.toString()?.trim().orEmpty()
        if (!Validator.isValidEmail(id)) {
            Toast.makeText(this, "Enter your email above first", Toast.LENGTH_SHORT).show()
            return
        }
        vm.forgotPassword(id)
    }

    private fun continueAsGuest() {
        prefs.setGuest(true)
        prefs.setLoggedIn(false)
        prefs.setUser("Guest", "guest@arogya.app", "")
        goHome()
    }

    private fun goHome() {
        startActivity(Intent(this, MainActivity::class.java))
        overridePendingTransition(R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}
