package com.arogyanidhi.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.arogyanidhi.app.R
import com.arogyanidhi.app.databinding.ActivitySignupBinding
import com.arogyanidhi.app.utils.PrefsManager
import com.arogyanidhi.app.utils.Validator
import com.arogyanidhi.app.viewmodels.AuthViewModel

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private val vm: AuthViewModel by viewModels()
    private lateinit var prefs: PrefsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefs = PrefsManager(this)

        binding.btnSignup.setOnClickListener { doSignup() }
        binding.tvGoLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        vm.loading.observe(this) {
            binding.progress.visibility = if (it) View.VISIBLE else View.GONE
            binding.btnSignup.isEnabled = !it
        }
        vm.error.observe(this) {
            if (!it.isNullOrEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                vm.clearMessages()
            }
        }
        vm.success.observe(this) {
            if (!it.isNullOrEmpty()) {
                val name = binding.etName.text?.toString()?.trim().orEmpty()
                val email = binding.etEmail.text?.toString()?.trim().orEmpty()
                val phone = binding.etPhone.text?.toString()?.trim().orEmpty()
                prefs.setLoggedIn(true)
                prefs.setGuest(false)
                prefs.setUser(name, email, phone)
                Toast.makeText(this, "Account created · Welcome!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                overridePendingTransition(R.anim.fade_in, android.R.anim.fade_out)
                finish()
                vm.clearMessages()
            }
        }
    }

    private fun doSignup() {
        val name = binding.etName.text?.toString()?.trim().orEmpty()
        val phone = binding.etPhone.text?.toString()?.trim().orEmpty()
        val email = binding.etEmail.text?.toString()?.trim().orEmpty()
        val pwd = binding.etPwd.text?.toString().orEmpty()
        val pwd2 = binding.etPwd2.text?.toString().orEmpty()

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || pwd.isEmpty() || pwd2.isEmpty()) {
            Toast.makeText(this, R.string.fill_all_fields, Toast.LENGTH_SHORT).show(); return
        }
        if (!Validator.isValidEmail(email)) {
            Toast.makeText(this, "Enter a valid email", Toast.LENGTH_SHORT).show(); return
        }
        if (!Validator.isValidPhone(phone)) {
            Toast.makeText(this, "Enter a valid phone", Toast.LENGTH_SHORT).show(); return
        }
        if (!Validator.isValidPassword(pwd)) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show(); return
        }
        if (pwd != pwd2) {
            Toast.makeText(this, R.string.passwords_mismatch, Toast.LENGTH_SHORT).show(); return
        }
        vm.signUp(email, pwd)
    }
}
