package com.example.studentcontactapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.studentcontactapp.utils.PrefManager
import com.example.studentcontactapp.utils.SettingsManager
import com.google.android.material.switchmaterial.SwitchMaterial

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var prefManager: PrefManager
    private lateinit var settingsManager: SettingsManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = PrefManager(requireContext())
        settingsManager = SettingsManager(requireContext())

        val tvUsername = view.findViewById<TextView>(R.id.tvProfileName)
        val switchDark = view.findViewById<SwitchMaterial>(R.id.switchDarkMode)
        val switchFont = view.findViewById<SwitchMaterial>(R.id.switchFontSize)
        val switchNotif = view.findViewById<SwitchMaterial>(R.id.switchNotification)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        tvUsername.text = "Username: ${prefManager.getUsername()}"

        switchDark.isChecked = settingsManager.isDarkMode()
        switchNotif.isChecked = settingsManager.isNotificationEnabled()
        switchFont.isChecked = settingsManager.getFontSize() > 12

        switchDark.setOnCheckedChangeListener { _, isChecked ->
            settingsManager.setDarkMode(isChecked)

            AppCompatDelegate.setDefaultNightMode(
                if (isChecked)
                    AppCompatDelegate.MODE_NIGHT_YES
                else
                    AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        switchFont.setOnCheckedChangeListener { _, isChecked ->
            val size = if (isChecked) 18 else 12
            settingsManager.setFontSize(size)
        }

        switchNotif.setOnCheckedChangeListener { _, isChecked ->
            settingsManager.setNotificationEnabled(isChecked)
        }

        btnLogout.setOnClickListener {
            prefManager.logout()
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}