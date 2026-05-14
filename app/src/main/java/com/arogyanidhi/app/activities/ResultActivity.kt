package com.arogyanidhi.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.arogyanidhi.app.R
import com.arogyanidhi.app.adapters.SchemeCardAdapter
import com.arogyanidhi.app.databinding.ActivityResultBinding
import com.arogyanidhi.app.models.QuizAnswers
import com.arogyanidhi.app.models.Scheme
import com.arogyanidhi.app.repository.ArogyaRepository
import com.arogyanidhi.app.utils.EligibilityEngine
import kotlinx.coroutines.launch

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private var eligible: List<Scheme> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val answers = pendingAnswers ?: QuizAnswers()
        eligible = EligibilityEngine.evaluate(answers)

        renderBanner()
        binding.rvSchemes.layoutManager = LinearLayoutManager(this)
        binding.rvSchemes.adapter = SchemeCardAdapter(eligible) { scheme ->
            val i = Intent(this, DocumentsActivity::class.java)
            i.putExtra(DocumentsActivity.EXTRA_SCHEME_ID, scheme.id)
            startActivity(i)
        }

        binding.btnBack.setOnClickListener { finish() }

        binding.btnSave.setOnClickListener {
            val repo = ArogyaRepository(this)
            lifecycleScope.launch {
                repo.saveResult(answers, eligible)
                Toast.makeText(this@ResultActivity, "Result saved!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDownload.setOnClickListener {
            Toast.makeText(this, "Download feature coming soon", Toast.LENGTH_SHORT).show()
        }

        binding.btnRetake.setOnClickListener {
            startActivity(Intent(this, QuizActivity::class.java))
            finish()
        }

        binding.btnSave.visibility = if (eligible.isEmpty()) View.GONE else View.VISIBLE
        binding.btnDownload.visibility = if (eligible.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun renderBanner() {
        if (eligible.isEmpty()) {
            binding.resultBanner.setBackgroundResource(R.drawable.bg_result_none)
            binding.tvBannerIcon.text = "ⓘ"
            binding.tvBannerTitle.text = getString(R.string.no_matches)
            binding.tvBannerDesc.text = getString(R.string.visit_phc)
            binding.tvResultSub.text = "0 schemes matched"
        } else {
            binding.resultBanner.setBackgroundResource(R.drawable.bg_result_success)
            binding.tvBannerIcon.text = "✓"
            binding.tvBannerTitle.text = getString(R.string.great_news)
            binding.tvBannerDesc.text = getString(R.string.eligible_for_schemes, eligible.size)
            binding.tvResultSub.text = "${eligible.size} scheme${if (eligible.size == 1) "" else "s"} matched"
        }
    }

    companion object {
        // Simple way to pass answers between activities without Parcelable
        var pendingAnswers: QuizAnswers? = null
    }
}
