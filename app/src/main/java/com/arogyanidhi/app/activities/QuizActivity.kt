package com.arogyanidhi.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.arogyanidhi.app.R
import com.arogyanidhi.app.databinding.ActivityQuizBinding
import com.arogyanidhi.app.viewmodels.QuizViewModel

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding
    private val vm: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.btnNext.setOnClickListener { handleNext() }
        binding.btnPrev.setOnClickListener { vm.prev() }

        vm.step.observe(this) { renderStep() }
        vm.answers.observe(this) { renderOptions() }

        renderStep()
    }

    private fun handleNext() {
        if (!vm.isCurrentAnswered()) return
        val notDone = vm.next()
        if (!notDone) {
            // Pass answers to result via singleton helper
            val ans = vm.answers.value!!
            ResultActivity.pendingAnswers = ans
            startActivity(Intent(this, ResultActivity::class.java))
            finish()
        }
    }

    private fun renderStep() {
        val s = vm.step.value ?: 0
        val total = vm.totalQuestions
        binding.tvQuizTitle.text = "Question ${s + 1} of $total"
        binding.tvQuestionNum.text = "QUESTION ${s + 1}"
        binding.tvQuestionText.text = vm.currentQuestion().text

        // Render step bar
        binding.stepBarContainer.removeAllViews()
        for (i in 0 until total) {
            val seg = View(this)
            val lp = LinearLayout.LayoutParams(0, dp(5), 1f)
            if (i > 0) lp.marginStart = dp(6)
            seg.layoutParams = lp
            seg.background = ContextCompat.getDrawable(
                this,
                when {
                    i < s -> R.drawable.step_dot_done
                    i == s -> R.drawable.step_dot_active
                    else -> R.drawable.step_dot_inactive
                }
            )
            binding.stepBarContainer.addView(seg)
        }

        binding.btnPrev.visibility = if (s == 0) View.GONE else View.VISIBLE
        binding.btnNext.text =
            if (s == total - 1) getString(R.string.see_results) else getString(R.string.next)

        renderOptions()
    }

    private fun renderOptions() {
        binding.optionsContainer.removeAllViews()
        val q = vm.currentQuestion()
        val curAns = vm.answers.value?.get(q.id)
        q.options.forEach { opt ->
            val tv = LayoutInflater.from(this).inflate(R.layout.item_quiz_option, binding.optionsContainer, false) as TextView
            tv.text = opt.label
            tv.isSelected = (curAns == opt.value)
            tv.setOnClickListener {
                vm.selectAnswer(opt.value)
                // refresh options to update selection state
                renderOptions()
            }
            val lp = (tv.layoutParams as ViewGroup.MarginLayoutParams)
            lp.topMargin = dp(8)
            tv.layoutParams = lp
            binding.optionsContainer.addView(tv)
        }
    }

    private fun dp(v: Int) = (v * resources.displayMetrics.density).toInt()
}
