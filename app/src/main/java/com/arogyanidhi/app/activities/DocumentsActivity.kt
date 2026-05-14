package com.arogyanidhi.app.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.arogyanidhi.app.adapters.DocSectionAdapter
import com.arogyanidhi.app.databinding.ActivityDocumentsBinding
import com.arogyanidhi.app.models.Scheme
import com.arogyanidhi.app.repository.ArogyaRepository
import com.arogyanidhi.app.utils.SchemeRepository
import kotlinx.coroutines.launch

class DocumentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDocumentsBinding
    private lateinit var repo: ArogyaRepository
    private val checkedMap = mutableMapOf<String, MutableSet<Int>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        repo = ArogyaRepository(this)

        binding.btnBack.setOnClickListener { finish() }

        val showAll = intent.getBooleanExtra(EXTRA_ALL_SCHEMES, false)
        val schemeId = intent.getStringExtra(EXTRA_SCHEME_ID)

        val schemes: List<Scheme> = when {
            showAll -> SchemeRepository.schemes
            schemeId != null -> listOfNotNull(SchemeRepository.findById(schemeId))
            else -> SchemeRepository.schemes
        }

        binding.tvSub.text = if (showAll) "All schemes"
        else schemes.firstOrNull()?.name ?: "Documents"

        // Load checked state, then render
        lifecycleScope.launch {
            schemes.forEach { s ->
                val checked = repo.getCheckedDocsFor(s.id).toMutableSet()
                checkedMap[s.id] = checked
            }
            val adapter = DocSectionAdapter(schemes, checkedMap) { sid, idx, checked ->
                lifecycleScope.launch { repo.setDocChecked(sid, idx, checked) }
            }
            binding.rvDocs.layoutManager = LinearLayoutManager(this@DocumentsActivity)
            binding.rvDocs.adapter = adapter
        }
    }

    companion object {
        const val EXTRA_SCHEME_ID = "scheme_id"
        const val EXTRA_ALL_SCHEMES = "all_schemes"
    }
}
