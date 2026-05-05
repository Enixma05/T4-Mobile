package com.example.studentcontactapp.utils

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "AppSettingsPrefs"
        private const val KEY_DARK_MODE = "dark_mode"
        private const val KEY_FONT_SIZE = "font_size"
        private const val KEY_NOTIFICATION = "notification_enabled"
    }

    fun setDarkMode(isDarkMode: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(KEY_DARK_MODE, isDarkMode)
            apply()
        }
    }

    fun isDarkMode(): Boolean {
        return sharedPreferences.getBoolean(KEY_DARK_MODE, false)
    }

    fun setFontSize(size: Int) {
        sharedPreferences.edit().apply {
            putInt(KEY_FONT_SIZE, size)
            apply()
        }
    }

    fun getFontSize(): Int {
        return sharedPreferences.getInt(KEY_FONT_SIZE, 12)
    }

    fun setNotificationEnabled(isEnabled: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(KEY_NOTIFICATION, isEnabled)
            apply()
        }
    }

    fun isNotificationEnabled(): Boolean {
        return sharedPreferences.getBoolean(KEY_NOTIFICATION, true)
    }
}