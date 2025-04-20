package com.ugikpoenya.sampleapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ugikpoenya.servermanager.AppManager
import com.ugikpoenya.servermanager.ServerManager
import com.ugikpoenya.servermanager.tools.HtmlListParser

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

        Log.d(LOG, "getApiResponse")
        serverManager.getApiResponse(this) { response ->
            Log.d(LOG, response?.name.toString())
            response?.categories?.forEach {
                Log.d(LOG, it.category.toString())
            }
        }

        val admob_rewarded_ads = serverManager.getItemKey(this, "admob_rewarded_ads")
        Log.d(LOG, admob_rewarded_ads.toString())

        AppManager().initAppMain(this)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AppManager().exitApp(this@MainActivity)
            }
        })
    }

    fun exitApp(view: View) {
        Log.d(LOG, "exitApp")
        AppManager().exitApp(this)
    }

    fun showPrivacyPolicy(view: View) {
        Log.d(LOG, "showPrivacyPolicy")
        AppManager().showPrivacyPolicy(this)
    }

    fun initDialogRedirect(view: View) {
        Log.d(LOG, "initDialogRedirect")
        AppManager().initDialogRedirect(this)
    }

    fun rateApp(view: View) {
        AppManager().rateApp(this)
    }

    fun shareApp(view: View) {
        AppManager().shareApp(this, getString(R.string.app_name))
    }

    fun nextApp(view: View) {
        AppManager().nextApp(this)
    }

    fun getApiResponse(view: View) {
        Log.d(LOG, "getApiResponse")
        serverManager.getApiResponse(this) { response ->
            Log.d(LOG, response?.name.toString())
            response?.categories?.forEach {
                Log.d(LOG, it.category.toString())
            }
        }
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

    fun getPostKey(view: View) {
        val key = "EAXPhBxVnYmm8PLi8Hof"
        Log.d(LOG, "getPostKey $key")
        serverManager.getPostKey(this, key) { postModel ->
            Log.d(LOG, postModel?.key.toString() + " => " + postModel?.post_title.toString() + " => " + postModel?.post_image.toString())
            Log.d(LOG, postModel?.post_content.toString())

            val list = postModel?.getContentList()
            list?.forEach {
                Log.d(LOG, it.text)
                it.children.forEach { itemData ->
                    Log.d(LOG, "-" + itemData.text)
                }
            }
            if (list !== null) {
                HtmlListParser().print(list)
            }
        }
    }
}