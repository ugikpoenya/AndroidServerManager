package com.ugikpoenya.sampleapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ugikpoenya.servermanager.ServerManager

class MainActivity : AppCompatActivity() {
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

        serverManager.setBaseUrl(this, "https://master.ugikpoenya.net/api/");
        serverManager.setApiKey(this, "DA8BB129F7C1ED5BD07046961C995A77s");
        serverManager.getApiResponse(this) { response: String? ->
            Log.d("LOG_SERVER", response.toString())
        }


    }
}