package com.ugikpoenya.sampleapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ugikpoenya.servermanager.ServerManager

class MainActivity : AppCompatActivity() {
    val LOG = "LOG_SERVER_SAMPLE"
    val serverManager = ServerManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        serverManager.setBaseUrl(this, "https://asia-southeast1-project-bangau.cloudfunctions.net/cms/api")
        serverManager.setApiKey(this, "DA8BB129F7C1ED5BD07046961C995A77")
        serverManager.getApiResponse(this) { response ->
            Log.d(LOG, response?.name.toString())
            response?.categories?.forEach {
                Log.d(LOG, it.category.toString())
            }
        }

        val admob_rewarded_ads = serverManager.getItemKey(this, "admob_rewarded_ads")
        Log.d(LOG, admob_rewarded_ads.toString())

    }

    fun getPostResponse(view: View) {
        Log.d(LOG, "getPostResponse")
        serverManager.getPostsResponse(this) { response ->
            response?.forEach {
                Log.d(LOG, it.key.toString() + " => " + it.post_title.toString() + " => " + it.post_image.toString())
            }
        }
    }

    fun getCategoriesResponse(view: View) {
        Log.d(LOG, "getCategoriesResponse")
        serverManager.getCategoriesResponse(this) { response ->
            var sampleKey: String? = null
            response?.forEach {
                Log.d(LOG, it.category.toString())
                sampleKey = it.category_key
            }

            if (!sampleKey.isNullOrEmpty()) {
                Log.d(LOG, "getCategoryKey : $sampleKey")
                serverManager.getCategoryKey(this, sampleKey.toString()) { postModelArrayList ->
                    postModelArrayList?.forEach { postModel ->
                        Log.d(LOG, postModel.key.toString() + " => " + postModel.post_title.toString() + " => " + postModel.post_image.toString())
                    }

                }
            }
        }
    }

    fun getAssetsResponse(view: View) {
        Log.d(LOG, "getAssetsResponse")
        serverManager.getAssetsResponse(this) { files, folders ->
            Log.d(LOG, "files : " + files?.size.toString())
            Log.d(LOG, "folders : " + folders?.size.toString())
            files?.forEach { filename ->
                Log.d(LOG, filename)
            }
            folders?.forEach {
                Log.d(LOG, it.key)
                it.value.forEach { filename ->
                    Log.d(LOG, filename)
                }
            }
        }
    }
}