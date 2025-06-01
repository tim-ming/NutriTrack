package com.fit2081.nutritrack_timming_32619138.util

import android.content.Context
import android.content.SharedPreferences

private const val PREFS_NAME = "activeId"
private const val KEY_USER_ID = "userId"

class SessionManager(private val appContext: Context) {
    private val sharedPreferences: SharedPreferences =
        appContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun login(userId: String){
        setUid(userId)
    }

    fun logout() {
        clearUid()
    }

    private fun setUid(userId: String) {
        editor.putString(KEY_USER_ID, userId).apply()
    }

    fun getUid(): String? {
        return sharedPreferences.getString(KEY_USER_ID, null)
    }

    fun hasUid(): Boolean {
        return sharedPreferences.contains(KEY_USER_ID)
    }

    private fun clearUid() {
        editor.remove(KEY_USER_ID).apply()
    }
}