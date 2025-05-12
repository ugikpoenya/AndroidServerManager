package com.ugikpoenya.sampleapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ugikpoenya.servermanager.AppManager
import com.ugikpoenya.servermanager.ServerManager
import com.ugikpoenya.servermanager.ServerPrefs
import com.ugikpoenya.servermanager.tools.DownloadCallback
import com.ugikpoenya.servermanager.tools.HtmlListParser
import com.ugikpoenya.servermanager.tools.VolleyDownload
import com.ugikpoenya.servermanager.tools.isDirectDownload
import java.io.File
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

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

        val admob_rewarded_ads = ServerPrefs(this).getItemKey("admob_rewarded_ads")
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
        val key = "FuAmbqjv8X9QRAxD69Il"
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

    fun downloadFile(view: View) {
        val fileDownload = "https://www.mediafire.com/file_premium/coe0otr6b7ez4f4/Basuri_Mengular_V1.mp3"
        val dirPath = Environment.getExternalStorageDirectory().path + "/" + Environment.DIRECTORY_DOWNLOADS
        val file = File(fileDownload)
        var filePath = URLDecoder.decode(file.name, StandardCharsets.UTF_8.toString())
        filePath = filePath.substringBefore("?")
        filePath = filePath.replace("/", "_")
        filePath = dirPath + "/" + filePath
        val fileStorage = File(filePath)

        if (fileStorage.exists()) {
            if (fileStorage.delete()) {
                Log.d(LOG, "FilePath Exist Deleted : ${filePath}")
            } else {
                Log.e(LOG, "FilePath Exist Failed To Deleted : ${filePath}")
            }
        } else {
            isDirectDownload(fileDownload) { isFile ->
                if (isFile) {
                    // Lanjutkan download pakai library kamu (misalnya PRDownloader, DownloadManager, dsb)

                    VolleyDownload(this, fileDownload, filePath, object : DownloadCallback {
                        override fun onProgressUpdate(progress: Int) {
                            runOnUiThread {
                                Log.d(LOG, "Progress ${progress}%")
                            }
                        }

                        override fun onDownloadComplete(filePath: String) {
                            runOnUiThread {
                                Log.d(LOG, filePath)
                                Toast.makeText(applicationContext, "Download completed!", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onDownloadFailed(errorMessage: String) {
                            runOnUiThread {
                                Log.e(LOG, "Error: ${errorMessage}")
                                Toast.makeText(applicationContext, "Download failed!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                } else {
                    // Buka browser
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(fileDownload))
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }
}