package com.ugikpoenya.servermanager.tools

import android.util.Log
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

fun isDirectDownload(url: String, callback: (Boolean) -> Unit) {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url(url)
        .head() // Hanya ambil header, tidak ambil body
        .build()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            Log.d("LOG", e.message.toString())
            Log.d("LOG", "isDirectDownload : error : $url")
            callback(false)
        }

        override fun onResponse(call: Call, response: Response) {
            val contentType = response.header("Content-Type")?.lowercase() ?: ""
            val isFile = response.isSuccessful && !contentType.contains("text/html")
            Log.d("LOG", "isDirectDownload : $isFile : $url")
            callback(isFile)
        }
    })
}