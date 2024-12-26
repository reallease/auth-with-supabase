package com.example.authwithsupabase.utils

import android.content.Context

// save and recovery data in shared storage the android

class SharedPreferenceHelper(private val context: Context) {

    fun saveDataString(data: String, key: String?) {
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        sharedPreferences.edit().putString(key, data).apply()
    }

    fun getStringData(key: String): String? {
        val sharedPreferences = context.getSharedPreferences(MY_PREF_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString(key, null)
    }

    companion object {
        private const val MY_PREF_KEY = "MY_PREF"
    }
}