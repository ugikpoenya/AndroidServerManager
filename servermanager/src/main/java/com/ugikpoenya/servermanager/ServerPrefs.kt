package com.ugikpoenya.servermanager

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.ugikpoenya.servermanager.model.ApiResponseModel
import com.ugikpoenya.servermanager.model.ItemModel
import androidx.core.content.edit
import org.json.JSONObject

class ServerPrefs(context: Context) {
    val prefs: SharedPreferences = context.getSharedPreferences(context.packageName, 0)
    var BASE_URL: String
        get() = prefs.getString("BASE_URL", "").toString()
        set(value) = prefs.edit { putString("BASE_URL", value) }
    var API_KEY: String
        get() = prefs.getString("API_KEY", "").toString()
        set(value) = prefs.edit { putString("API_KEY", value) }
    var RESPONSE: String
        get() = prefs.getString("RESPONSE", "").toString()
        set(value) = prefs.edit { putString("RESPONSE", value) }

    var open_ads_last_shown_time: Long
        get() = prefs.getLong("open_ads_last_shown_time", 0).toLong()
        set(value) = prefs.edit { putLong("open_ads_last_shown_time", value) }

    var interstitial_last_shown_time: Long
        get() = prefs.getLong("interstitial_last_shown_time", 0).toLong()
        set(value) = prefs.edit { putLong("interstitial_last_shown_time", value) }

    var rewarded_ads_last_shown_time: Long
        get() = prefs.getLong("rewarded_ads_last_shown_time", 0).toLong()
        set(value) = prefs.edit { putLong("rewarded_ads_last_shown_time", value) }

    fun getApiResponseModel(): ApiResponseModel? {
        return try {
            val response = prefs.getString("RESPONSE", "").toString()
            Gson().fromJson(response, ApiResponseModel::class.java)
        } catch (e: Exception) {
            Log.d("LOG_SERVER_PREFS", "Error : " + e.message)
            null
        }
    }

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

    fun getItemKey(key: String): String? {
        try {
            val response = prefs.getString("RESPONSE", "").toString()
            val jsonObject = JSONObject(response)
            val item = jsonObject.getJSONObject("item")
            return item.getString(key.trim())
        } catch (e: Exception) {
            Log.d("LOG_SERVER_PREFS", "Error : " + e.message)
            return null
        }
    }

    var privacy_policy: Boolean
        get() = prefs.getBoolean("privacy_policy", false)
        set(value) = prefs.edit { putBoolean("privacy_policy", value) }
}