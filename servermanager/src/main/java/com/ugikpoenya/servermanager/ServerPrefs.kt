package com.ugikpoenya.servermanager

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.ugikpoenya.servermanager.model.ApiResponseModel
import com.ugikpoenya.servermanager.model.ItemModel

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

    fun getItemModel(): ItemModel? {
        return try {
            val response = prefs.getString("RESPONSE", "").toString()
            val apiResponseModel = Gson().fromJson(response, ApiResponseModel::class.java)
            apiResponseModel.item
        } catch (e: Exception) {
            Log.d("LOG_SERVER_PREFS", "Error : " + e.message)
            null
        }
    }

    var privacy_policy: Boolean
        get() = prefs.getBoolean("privacy_policy", false)
        set(value) = prefs.edit().putBoolean("privacy_policy", value).apply()
}