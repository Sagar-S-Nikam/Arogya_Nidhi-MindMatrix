package com.arogyanidhi.app.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.arogyanidhi.app.R
import com.arogyanidhi.app.activities.LoginActivity
import com.arogyanidhi.app.databinding.FragmentProfileBinding
import com.arogyanidhi.app.repository.AuthRepository
import com.arogyanidhi.app.utils.PrefsManager

class ProfileFragment : Fragment() {

    private var _b: FragmentProfileBinding? = null
    private val b get() = _b!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _b = FragmentProfileBinding.inflate(inflater, container, false)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val prefs = PrefsManager(requireContext())
        val name = prefs.getName().ifEmpty { "User" }
        b.tvName.text = name
        b.tvEmail.text = prefs.getEmail()
        b.tvAvatar.text = (name.firstOrNull()?.uppercase() ?: "U")

        // Configure each row
        setupRow(b.rowEdit.root, R.drawable.ic_edit, R.color.cyan_mid,
            "Edit Profile", "Update your details") {
            Toast.makeText(requireContext(), "Edit profile screen coming soon", Toast.LENGTH_SHORT).show()
        }
        setupRow(b.rowPhone.root, R.drawable.ic_phone, R.color.cyan_mid,
            "Phone Number", prefs.getPhone().ifEmpty { "Not set" }) {}

        setupRow(b.rowDark.root, R.drawable.ic_moon, R.color.cyan_mid,
            "Dark Mode", "Toggle theme") {
            toggleDarkMode()
        }
        setupRow(b.rowNotif.root, R.drawable.ic_bell, R.color.cyan_mid,
            "Notifications", "Scheme updates") {
            Toast.makeText(requireContext(), "Notifications coming soon", Toast.LENGTH_SHORT).show()
        }

        setupRow(b.rowVersion.root, R.drawable.ic_info, R.color.cyan_mid,
            "App Version", "1.0.0 · Build 1") {}
        setupRow(b.rowPrivacy.root, R.drawable.ic_lock, R.color.cyan_mid,
            "Privacy Policy", null) {
            Toast.makeText(requireContext(), "Privacy policy coming soon", Toast.LENGTH_SHORT).show()
        }
        setupRow(b.rowAbout.root, R.drawable.ic_help, R.color.cyan_mid,
            "About App", null) {
            Toast.makeText(requireContext(), "Arogya-Nidhi v1.0.0", Toast.LENGTH_SHORT).show()
        }

        setupRow(b.rowLogout.root, R.drawable.ic_logout, R.color.error,
            "Logout", "Sign out of account", redTitle = true,
            iconBgRes = R.drawable.circle_tint_red) {
            doLogout()
        }
    }

    private fun setupRow(
        rowView: View,
        iconRes: Int,
        iconTintColorRes: Int,
        title: String,
        sub: String?,
        redTitle: Boolean = false,
        iconBgRes: Int = R.drawable.circle_tint_cyan,
        onClick: () -> Unit
    ) {
        val icon = rowView.findViewById<ImageView>(R.id.icon)
        val iconBox = rowView.findViewById<View>(R.id.iconBox)
        val titleTv = rowView.findViewById<TextView>(R.id.title)
        val subTv = rowView.findViewById<TextView>(R.id.sub)

        iconBox.setBackgroundResource(iconBgRes)
        icon.setImageResource(iconRes)
        icon.setColorFilter(ContextCompat.getColor(requireContext(), iconTintColorRes))

        titleTv.text = title
        if (sub.isNullOrEmpty()) {
            subTv.visibility = View.GONE
        } else {
            subTv.visibility = View.VISIBLE
            subTv.text = sub
        }
        if (redTitle) titleTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.error))

        rowView.setOnClickListener { onClick() }
    }

    private fun toggleDarkMode() {
        val prefs = PrefsManager(requireContext())
        val isDark = !prefs.isDarkMode()
        prefs.setDarkMode(isDark)
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
        requireActivity().recreate()
    }

    private fun doLogout() {
        PrefsManager(requireContext()).logout()
        AuthRepository().signOut()
        startActivity(Intent(requireContext(), LoginActivity::class.java))
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _b = null
    }
}
