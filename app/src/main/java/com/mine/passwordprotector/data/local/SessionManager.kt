package com.mine.passwordprotector.data.local

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun setLoggedIn(isLoggedIn: Boolean) {
        prefs.edit().putBoolean("is_logged_in", isLoggedIn).apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("is_logged_in", false)
}