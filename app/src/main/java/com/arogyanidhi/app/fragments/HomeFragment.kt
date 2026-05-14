package com.arogyanidhi.app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.arogyanidhi.app.R
import com.arogyanidhi.app.activities.DocumentsActivity
import com.arogyanidhi.app.activities.QuizActivity
import com.arogyanidhi.app.databinding.FragmentHomeBinding
import com.arogyanidhi.app.repository.ArogyaRepository
import com.arogyanidhi.app.utils.PrefsManager
import com.arogyanidhi.app.utils.SchemeRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _b: FragmentHomeBinding? = null
    private val b get() = _b!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _b = FragmentHomeBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = PrefsManager(requireContext())
        val name = prefs.getName().ifEmpty { "Friend" }
        b.tvUserName.text = "$name 👋"
        b.tvAvatar.text = (name.firstOrNull()?.uppercase() ?: "U")

        // Stats
        setStat(b.stat1.root, SchemeRepository.schemes.size.toString(), "Schemes")
        setStat(b.stat2.root, "0", "Saved")
        setStat(b.stat3.root, "10+", "Districts")

        val repo = ArogyaRepository(requireContext())
        lifecycleScope.launch {
            val count = repo.getSavedResults().size
            setStat(b.stat2.root, count.toString(), "Saved")
        }

        configureCard(b.cardEligibility.root, R.drawable.circle_tint_cyan,
            R.drawable.ic_check_clipboard, R.color.cyan_deep,
            "Check Eligibility", "Take quick quiz") {
            startActivity(Intent(requireContext(), QuizActivity::class.java))
        }
        configureCard(b.cardDocs.root, R.drawable.circle_tint_blue,
            R.drawable.ic_document, R.color.cyan_mid,
            "Required Documents", "Browse all docs") {
            val i = Intent(requireContext(), DocumentsActivity::class.java)
            i.putExtra(DocumentsActivity.EXTRA_ALL_SCHEMES, true)
            startActivity(i)
        }
        configureCard(b.cardSaved.root, R.drawable.circle_tint_orange,
            R.drawable.ic_bookmark, R.color.warning,
            "Saved Results", "View history") {
            switchTab(R.id.nav_saved)
        }
        configureCard(b.cardSchemes.root, R.drawable.circle_tint_purple,
            R.drawable.ic_heart, R.color.error,
            "Health Schemes", "All ${SchemeRepository.schemes.size} schemes") {
            switchTab(R.id.nav_schemes)
        }

        b.btnStartCheck.setOnClickListener {
            startActivity(Intent(requireContext(), QuizActivity::class.java))
        }
    }

    private fun setStat(root: View, value: String, label: String) {
        root.findViewById<TextView>(R.id.tvStatValue).text = value
        root.findViewById<TextView>(R.id.tvStatLabel).text = label
    }

    private fun configureCard(
        root: View, bgRes: Int, iconRes: Int, tintColor: Int,
        title: String, sub: String, onClick: () -> Unit
    ) {
        root.findViewById<View>(R.id.iconBg).setBackgroundResource(bgRes)
        val iv = root.findViewById<ImageView>(R.id.cardIcon)
        iv.setImageResource(iconRes)
        iv.setColorFilter(ContextCompat.getColor(requireContext(), tintColor))
        root.findViewById<TextView>(R.id.cardTitle).text = title
        root.findViewById<TextView>(R.id.cardSub).text = sub
        root.setOnClickListener { onClick() }
    }

    private fun switchTab(menuId: Int) {
        val nav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
        nav?.selectedItemId = menuId
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
