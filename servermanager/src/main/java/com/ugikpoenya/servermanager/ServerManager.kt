package com.ugikpoenya.servermanager

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.ugikpoenya.servermanager.model.ApiResponseModel
import com.ugikpoenya.servermanager.model.AssetResponseModel
import com.ugikpoenya.servermanager.model.CategoryModel
import com.ugikpoenya.servermanager.model.CategoryResponseModel
import com.ugikpoenya.servermanager.model.PostModel
import com.ugikpoenya.servermanager.model.PostResponseModel
import org.json.JSONObject

class ServerManager {
    val LOG = "LOG_SERVER"
    fun setBaseUrl(context: Context, base_url: String) {
        ServerPrefs(context).BASE_URL = base_url
    }

    fun setApiKey(context: Context, api_key: String) {
        ServerPrefs(context).API_KEY = api_key
    }

    fun getApiResponse(context: Context, function: (apiResponseModel: ApiResponseModel?) -> (Unit)) {
        if (ServerPrefs(context).BASE_URL.isEmpty()) {
            Log.d(LOG, "Base url not found")
            function(null)
        } else {
            val queue = Volley.newRequestQueue(context)
            val stringRequest = object : StringRequest(Method.GET, ServerPrefs(context).BASE_URL, com.android.volley.Response.Listener { response ->
                try {
                    Log.d(LOG, "getItem successfully")
                    ServerPrefs(context).RESPONSE = response
                    Log.d("LOG_SERVER", response)
                    val apiResponseModel = Gson().fromJson(response, ApiResponseModel::class.java)
                    function(apiResponseModel)
                } catch (e: Exception) {
                    Log.d(LOG, "Error : " + e.message)
                    function(null)
                }
            }, com.android.volley.Response.ErrorListener {
                Log.d(LOG, "Error : " + it.message.toString())
                function(null)
            }) {
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["package_name"] = context.packageName
                    headers["api_key"] = ServerPrefs(context).API_KEY
                    return headers
                }
            }
            queue.add(stringRequest)
        }
    }

    fun getItemKey(context: Context, key: String): String? {
        try {
            val jsonObject = JSONObject(ServerPrefs(context).RESPONSE)
            val item = jsonObject.getJSONObject("item")
            return item.getString(key.trim())
        } catch (e: Exception) {
            Log.d(LOG, "Error : " + e.message)
            return null
        }
    }


    //POST MODULE
    fun getPostsResponse(context: Context, function: (postModelArrayList: ArrayList<PostModel>?) -> (Unit)) {
        val queue = Volley.newRequestQueue(context)
        val stringRequest = object : StringRequest(Method.GET, ServerPrefs(context).BASE_URL + "/posts", com.android.volley.Response.Listener { response ->
            try {
                val postResponse = Gson().fromJson(response, PostResponseModel::class.java)
                function(postResponse.data)
            } catch (e: Exception) {
                Log.d("LOG", "Error : " + e.message)
                function(null)
            }
        }, com.android.volley.Response.ErrorListener {
            Log.d("LOG", "Error : " + it.message)
            function(null)
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["package_name"] = context.packageName
                headers["api_key"] = ServerPrefs(context).API_KEY
                return headers
            }
        }
        queue.add(stringRequest)
    }

    fun getPostKey(context: Context, key: String, function: (postModel: PostModel?) -> (Unit)) {
        val queue = Volley.newRequestQueue(context)
        val stringRequest = object : StringRequest(Method.GET, ServerPrefs(context).BASE_URL + "/posts/" + key, com.android.volley.Response.Listener { response ->
            try {
                val postResponse = Gson().fromJson(response, PostResponseModel::class.java)
                function(postResponse.post)
            } catch (e: Exception) {
                Log.d("LOG", "Error : " + e.message)
                function(null)
            }
        }, com.android.volley.Response.ErrorListener {
            Log.d("LOG", "Error : " + it.message)
            function(null)
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["package_name"] = context.packageName
                headers["api_key"] = ServerPrefs(context).API_KEY
                return headers
            }
        }
        queue.add(stringRequest)
    }

    //Category MODULE
    fun getCategoriesResponse(context: Context, function: (categoryModelArrayList: ArrayList<CategoryModel>?) -> (Unit)) {
        val queue = Volley.newRequestQueue(context)
        val stringRequest = object : StringRequest(Method.GET, ServerPrefs(context).BASE_URL + "/categories", com.android.volley.Response.Listener { response ->
            try {
                val postResponse = Gson().fromJson(response, CategoryResponseModel::class.java)
                function(postResponse.data)
            } catch (e: Exception) {
                Log.d("LOG", "Error : " + e.message)
                function(null)
            }
        }, com.android.volley.Response.ErrorListener {
            Log.d("LOG", "Error : " + it.message)
            function(null)
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["package_name"] = context.packageName
                headers["api_key"] = ServerPrefs(context).API_KEY
                return headers
            }
        }
        queue.add(stringRequest)
    }

    fun getCategoryKey(context: Context, key: String, function: (postModelArrayList: ArrayList<PostModel>?) -> (Unit)) {
        val queue = Volley.newRequestQueue(context)
        val stringRequest = object : StringRequest(Method.GET, ServerPrefs(context).BASE_URL + "/categories/" + key, com.android.volley.Response.Listener { response ->
            try {
                val postResponse = Gson().fromJson(response, PostResponseModel::class.java)
                function(postResponse.data)
            } catch (e: Exception) {
                Log.d("LOG", "Error : " + e.message)
                function(null)
            }
        }, com.android.volley.Response.ErrorListener {
            Log.d("LOG", "Error : " + it.message)
            function(null)
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["package_name"] = context.packageName
                headers["api_key"] = ServerPrefs(context).API_KEY
                return headers
            }
        }
        queue.add(stringRequest)
    }


    //Assets module
    fun getAssetsResponse(context: Context, function: (files: ArrayList<String>?, folders: Map<String, ArrayList<String>>?) -> (Unit)) {
        val queue = Volley.newRequestQueue(context)
        val stringRequest = object : StringRequest(Method.GET, ServerPrefs(context).BASE_URL + "/assets", com.android.volley.Response.Listener { response ->
            try {
                val postResponse = Gson().fromJson(response, AssetResponseModel::class.java)
                function(postResponse.files, postResponse.folders)
            } catch (e: Exception) {
                Log.d("LOG", "Error : " + e.message)
                function(null, null)
            }
        }, com.android.volley.Response.ErrorListener {
            Log.d("LOG", "Error : " + it.message)
            function(null, null)
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["package_name"] = context.packageName
                headers["api_key"] = ServerPrefs(context).API_KEY
                return headers
            }
        }
        queue.add(stringRequest)
    }
}