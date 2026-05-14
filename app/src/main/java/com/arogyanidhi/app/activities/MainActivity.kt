package com.arogyanidhi.app.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.arogyanidhi.app.R
import com.arogyanidhi.app.databinding.ActivityMainBinding
import com.arogyanidhi.app.fragments.HomeFragment
import com.arogyanidhi.app.fragments.ProfileFragment
import com.arogyanidhi.app.fragments.SavedFragment
import com.arogyanidhi.app.fragments.SchemesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            loadFragment(HomeFragment())
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            val fragment: Fragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_schemes -> SchemesFragment()
                R.id.nav_saved -> SavedFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> HomeFragment()
            }
            loadFragment(fragment)
            true
        }
    }

    private fun loadFragment(f: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.fade_in, android.R.anim.fade_out)
            .replace(R.id.fragmentContainer, f)
            .commit()
    }
}
