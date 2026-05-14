package com.arogyanidhi.app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.arogyanidhi.app.activities.QuizActivity
import com.arogyanidhi.app.adapters.SavedResultsAdapter
import com.arogyanidhi.app.databinding.FragmentSavedBinding
import com.arogyanidhi.app.viewmodels.SavedResultsViewModel

class SavedFragment : Fragment() {

    private var _b: FragmentSavedBinding? = null
    private val b get() = _b!!
    private val vm: SavedResultsViewModel by viewModels()
    private lateinit var adapter: SavedResultsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _b = FragmentSavedBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = SavedResultsAdapter(emptyList()) { id ->
            vm.delete(id)
        }
        b.rvSaved.layoutManager = LinearLayoutManager(requireContext())
        b.rvSaved.adapter = adapter

        vm.savedResults.observe(viewLifecycleOwner) { list ->
            adapter.submit(list)
            b.emptyState.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
            b.rvSaved.visibility = if (list.isEmpty()) View.GONE else View.VISIBLE
        }

        b.btnGoQuiz.setOnClickListener {
            startActivity(Intent(requireContext(), QuizActivity::class.java))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
