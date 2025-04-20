package com.ugikpoenya.servermanager

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton

class AppManager {
    val LOG = "LOG_SERVER_APP"

    fun initAppMain(context: Context) {
        if (ServerPrefs(context).privacy_policy) {
            Log.d(LOG, "Privacy policy accept")
        } else {
            Log.d(LOG, "Privacy policy decline")
            showPrivacyPolicy(context)
        }
    }

    fun exitApp(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        dialog.setContentView(R.layout.dialog_exit)
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT

        val btn_yes = (dialog.findViewById(R.id.btn_yes)) as AppCompatButton
        val btn_no = (dialog.findViewById(R.id.btn_no)) as AppCompatButton

        btn_yes.setOnClickListener {
            dialog.dismiss()
            (context as Activity).finish()
        }
        btn_no.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        dialog.window?.attributes = lp
    }

    fun showPrivacyPolicy(context: Context) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog.window?.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT

        val itemModel = ServerPrefs(context).getItemModel()

        if (itemModel?.privacy_policy.isNullOrEmpty()) {
            dialog.setContentView(R.layout.dialog_privacy_policy)
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        } else {
            dialog.setContentView(R.layout.dialog_privacy_policy_web)
            lp.height = WindowManager.LayoutParams.MATCH_PARENT
        }

        val bt_accept = (dialog.findViewById(R.id.bt_accept)) as AppCompatButton
        val bt_decline = (dialog.findViewById(R.id.bt_decline)) as AppCompatButton

        if (itemModel?.privacy_policy.isNullOrEmpty()) {
            val tv_content = (dialog.findViewById(R.id.tv_content)) as TextView
            tv_content.movementMethod = LinkMovementMethod.getInstance()
        } else {
            val webView = (dialog.findViewById(R.id.webView)) as WebView
            webView.loadUrl(itemModel.privacy_policy.toString())
        }
        bt_accept.setOnClickListener {
            ServerPrefs(context).privacy_policy = true
            dialog.dismiss()
            Log.d(LOG, "Privacy policy accept")
        }
        bt_decline.setOnClickListener {
            ServerPrefs(context).privacy_policy = false
            dialog.dismiss()
            Log.d(LOG, "Privacy policy decline")
        }
        dialog.show()
        dialog.window?.attributes = lp
    }
}