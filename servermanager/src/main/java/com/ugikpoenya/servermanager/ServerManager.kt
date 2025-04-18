package com.ugikpoenya.servermanager

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class ServerManager {
    val LOG="LOG_SERVER"
    fun setBaseUrl(context: Context, base_url: String) {
        ServerPrefs(context).BASE_URL = base_url
    }

    fun setApiKey(context: Context, api_key: String) {
        ServerPrefs(context).API_KEY = api_key
    }

    fun getApiResponse(context: Context, function: (response: String?) -> (Unit)) {
        if (ServerPrefs(context).BASE_URL.isEmpty()) {
            Log.d(LOG, "Base url not found")
            function(null)
        } else {
            val queue = Volley.newRequestQueue(context)
            val stringRequest = object : StringRequest(Method.GET, ServerPrefs(context).BASE_URL, com.android.volley.Response.Listener { response ->
                try {
                    Log.d(LOG, "getItem successfully")
                    ServerPrefs(context).RESPONSE = response
                    function(response)
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
}