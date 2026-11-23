package com.example.aplikasidatassiswa.data

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppPreferences(context: Context) {

    private val prefs = context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _isLoggedIn = MutableStateFlow(prefs.getBoolean(KEY_LOGGED_IN, false))
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _darkTheme = MutableStateFlow(prefs.getBoolean(KEY_DARK_THEME, false))
    val darkTheme: StateFlow<Boolean> = _darkTheme.asStateFlow()

    var username: String = prefs.getString(KEY_USERNAME, "").orEmpty()
        private set

    fun login(user: String) {
        username = user
        prefs.edit()
            .putBoolean(KEY_LOGGED_IN, true)
            .putString(KEY_USERNAME, user)
            .apply()
        _isLoggedIn.value = true
    }

    fun logout() {
        username = ""
        prefs.edit()
            .putBoolean(KEY_LOGGED_IN, false)
            .remove(KEY_USERNAME)
            .apply()
        _isLoggedIn.value = false
    }

    fun setDarkTheme(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_DARK_THEME, enabled).apply()
        _darkTheme.value = enabled
    }

    companion object {
        private const val PREFS_NAME = "aplikasi_data_siswa_prefs"
        private const val KEY_LOGGED_IN = "logged_in"
        private const val KEY_USERNAME = "username"
        private const val KEY_DARK_THEME = "dark_theme"

        const val DEMO_USERNAME = "admin"
        const val DEMO_PASSWORD = "admin123"
    }
}
