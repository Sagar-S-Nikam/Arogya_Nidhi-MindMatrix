package com.arogyanidhi.app.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.arogyanidhi.app.activities.DocumentsActivity
import com.arogyanidhi.app.adapters.CategoryChipAdapter
import com.arogyanidhi.app.adapters.SchemeRowAdapter
import com.arogyanidhi.app.databinding.FragmentSchemesBinding
import com.arogyanidhi.app.utils.SchemeRepository

class SchemesFragment : Fragment() {

    private var _b: FragmentSchemesBinding? = null
    private val b get() = _b!!

    private var query = ""
    private var category = "All"
    private lateinit var rowAdapter: SchemeRowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _b = FragmentSchemesBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Categories
        val catAdapter = CategoryChipAdapter(SchemeRepository.categories(), category) { c ->
            category = c
            refresh()
        }
        b.rvCategories.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        b.rvCategories.adapter = catAdapter

        // Schemes
        rowAdapter = SchemeRowAdapter(SchemeRepository.schemes) { scheme ->
            val i = Intent(requireContext(), DocumentsActivity::class.java)
            i.putExtra(DocumentsActivity.EXTRA_SCHEME_ID, scheme.id)
            startActivity(i)
        }
        b.rvSchemes.layoutManager = LinearLayoutManager(requireContext())
        b.rvSchemes.adapter = rowAdapter

        // Search
        b.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                query = s?.toString().orEmpty()
                refresh()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        refresh()
    }

    private fun refresh() {
        val list = SchemeRepository.search(query, category)
        rowAdapter.submit(list)
        b.tvEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
