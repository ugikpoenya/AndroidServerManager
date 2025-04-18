package com.ugikpoenya.servermanager

import android.content.Context
import android.content.SharedPreferences

class ServerPrefs(context: Context) {
    val prefs: SharedPreferences = context.getSharedPreferences(context.packageName, 0)
    var BASE_URL: String
        get() = prefs.getString("BASE_URL", "").toString()
        set(value) = prefs.edit().putString("BASE_URL", value).apply()
    var API_KEY: String
        get() = prefs.getString("API_KEY", "").toString()
        set(value) = prefs.edit().putString("API_KEY", value).apply()
    var RESPONSE: String
        get() = prefs.getString("RESPONSE", "").toString()
        set(value) = prefs.edit().putString("RESPONSE", value).apply()
}