package com.arogyanidhi.app.activities

import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.arogyanidhi.app.R
import com.arogyanidhi.app.adapters.OnboardingAdapter
import com.arogyanidhi.app.adapters.OnboardingSlide
import com.arogyanidhi.app.databinding.ActivityOnboardingBinding
import com.arogyanidhi.app.utils.PrefsManager

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private val slides by lazy {
        listOf(
            OnboardingSlide(R.drawable.ic_check_clipboard,
                getString(R.string.onb1_title), getString(R.string.onb1_desc)),
            OnboardingSlide(R.drawable.ic_document,
                getString(R.string.onb2_title), getString(R.string.onb2_desc)),
            OnboardingSlide(R.drawable.ic_heart,
                getString(R.string.onb3_title), getString(R.string.onb3_desc))
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = OnboardingAdapter(slides)

        setupDots()
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                updateDots(position)
                val isLast = position == slides.lastIndex
                binding.btnNext.text =
                    if (isLast) getString(R.string.get_started) else getString(R.string.next)
                binding.btnSkip.visibility =
                    if (isLast) android.view.View.INVISIBLE else android.view.View.VISIBLE
            }
        })

        binding.btnNext.setOnClickListener {
            val cur = binding.viewPager.currentItem
            if (cur < slides.lastIndex) {
                binding.viewPager.currentItem = cur + 1
            } else {
                finishOnboarding()
            }
        }
        binding.btnSkip.setOnClickListener { finishOnboarding() }
    }

    private fun setupDots() {
        binding.dotsLayout.removeAllViews()
        slides.forEachIndexed { i, _ ->
            val dot = ImageView(this)
            dot.setImageDrawable(makeDot(i == 0))
            val lp = ViewGroup.MarginLayoutParams(
                if (i == 0) dp(24) else dp(8),
                dp(8)
            )
            lp.marginStart = dp(4)
            lp.marginEnd = dp(4)
            dot.layoutParams = lp
            binding.dotsLayout.addView(dot)
        }
    }

    private fun updateDots(active: Int) {
        for (i in 0 until binding.dotsLayout.childCount) {
            val v = binding.dotsLayout.getChildAt(i) as ImageView
            v.setImageDrawable(makeDot(i == active))
            val lp = v.layoutParams as ViewGroup.MarginLayoutParams
            lp.width = if (i == active) dp(24) else dp(8)
            v.layoutParams = lp
        }
    }

    private fun makeDot(active: Boolean): GradientDrawable {
        val d = GradientDrawable()
        d.shape = GradientDrawable.RECTANGLE
        d.cornerRadius = dp(4).toFloat()
        d.setColor(
            ContextCompat.getColor(
                this,
                if (active) R.color.cyan_mid else R.color.border
            )
        )
        return d
    }

    private fun dp(v: Int): Int = (v * resources.displayMetrics.density).toInt()

    private fun finishOnboarding() {
        PrefsManager(this).setOnboardingDone()
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(R.anim.fade_in, android.R.anim.fade_out)
        finish()
    }
}
