package com.ugikpoenya.servermanager

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ugikpoenya.servermanager.model.PostModel

class AppDataPrefs(context: Context) {
    val prefs: SharedPreferences = context.getSharedPreferences(context.packageName + "_AppDataPrefs", 0)
    var post_model_array_list: List<PostModel>
        get() {
            return try {
                val json = prefs.getString("post_model_array_list", "")
                val gsonBuilder = GsonBuilder().serializeNulls()
                val gson = gsonBuilder.create()
                gson.fromJson(json, Array<PostModel>::class.java).toList()
            } catch (e: Exception) {
                Log.d("LOG", "Error : " + e.message)
                ArrayList()
            }
        }
        set(value) {
            try {
                prefs.edit().putString("post_model_array_list", Gson().toJson(value)).apply()
            } catch (e: Exception) {
                Log.d("LOG", "Error : " + e.message)
            }
        }

    // Tambah post ke list
    fun addPost(post: PostModel) {
        val list = post_model_array_list.toMutableList()
        list.add(post)
        post_model_array_list = list
    }

    fun removePostByKey(key: String) {
        val list = post_model_array_list.toMutableList()
        list.removeAll { it.key == key }
        post_model_array_list = list
    }

    // Clear semua
    fun clearAllPosts() {
        post_model_array_list = emptyList()
    }

    var serach_array_list: List<String>
        get() {
            return try {
                val json = prefs.getString("serach_array_list", "")
                val gsonBuilder = GsonBuilder().serializeNulls()
                val gson = gsonBuilder.create()
                gson.fromJson(json, Array<String>::class.java).toList()
            } catch (e: Exception) {
                Log.d("LOG", "Error : " + e.message)
                ArrayList()
            }
        }
        set(value) {
            try {
                prefs.edit().putString("serach_array_list", Gson().toJson(value)).apply()
            } catch (e: Exception) {
                Log.d("LOG", "Error : " + e.message)
            }
        }
}