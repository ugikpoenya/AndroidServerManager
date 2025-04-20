package com.ugikpoenya.servermanager

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View.GONE
import android.view.Window
import android.view.WindowManager
import android.webkit.URLUtil
import android.webkit.WebView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.net.toUri
import com.squareup.picasso.Picasso

class AppManager {
    val LOG = "LOG_SERVER_APP"

    fun initAppMain(context: Context) {
        if (ServerPrefs(context).privacy_policy) {
            Log.d(LOG, "Privacy policy accept")
        } else {
            Log.d(LOG, "Privacy policy decline")
            showPrivacyPolicy(context)
        }
        initDialogRedirect(context)
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

    fun initDialogRedirect(context: Context) {
        val itemModel = ServerPrefs(context).getItemModel()
        if (itemModel !== null && itemModel.redirect_url.isNotEmpty() && URLUtil.isValidUrl(itemModel.redirect_url)) {
            val uri = itemModel.redirect_url.toUri()
            val id = uri.getQueryParameter("id")

            if (!id.isNullOrEmpty()) {
                val dialog = Dialog(context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE) // before
                dialog.setContentView(R.layout.dialog_redirect)
                if (itemModel.redirect_cancelable) dialog.setCancelable(true)
                else dialog.setCancelable(false)

                val lp = WindowManager.LayoutParams()
                lp.copyFrom(dialog.window?.attributes)
                lp.width = WindowManager.LayoutParams.MATCH_PARENT
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT

                if (itemModel.redirect_image_url.isEmpty()) {
                    (dialog.findViewById<ImageView>(R.id.imageView)!!).visibility = GONE
                } else {
                    Picasso.get()
                        .load(itemModel.redirect_image_url)
                        .resize(50, 50)
                        .centerCrop()
                        .placeholder(R.drawable.ic_thumbnail)
                        .error(R.drawable.ic_thumbnail)
                        .into((dialog.findViewById<ImageView>(R.id.imageView)!!))
                }

                (dialog.findViewById<TextView>(R.id.txtTitle)!!).text = itemModel.redirect_title
                (dialog.findViewById<TextView>(R.id.txtContent)!!).text = itemModel.redirect_content

                val btnUpdate: Button = dialog.findViewById(R.id.btnUpdate)
                val intent = (context as Activity).packageManager.getLaunchIntentForPackage(id)

                if (intent == null) {
                    btnUpdate.text = itemModel.redirect_button
                } else {
                    btnUpdate.text = "Open"
                }

                (dialog.findViewById<Button>(R.id.btnUpdate)!!).setOnClickListener {
                    if (intent == null) {
                        Log.d("LOG", "Open URL " + itemModel.redirect_url)
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW, itemModel.redirect_url.toUri()
                            )
                        )
                    } else {
                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
                        context.startActivity(intent)
                    }
                }
                (dialog.findViewById<Button>(R.id.btnClose)!!).setOnClickListener {
                    dialog.dismiss()
                    if (!itemModel.redirect_cancelable) {
                        context.finish()
                    }
                }
                dialog.show()
                dialog.window?.attributes = lp
            }
        }
    }

    fun rateApp(context: Context) {
        val packageName = (context as Activity).packageName
        val rateUrl = "https://play.google.com/store/apps/details?id=$packageName"
        Log.d("LOG", "Open URL $rateUrl")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(rateUrl))
        context.startActivity(intent)
    }

    fun shareApp(context: Context, appName: String?) {
        val packageName = (context as Activity).packageName
        val rateUrl = "https://play.google.com/store/apps/details?id=$packageName"
        val contentShare: String = context.resources.getString(R.string.SHARE_APP_TEXT, appName, rateUrl)

        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, contentShare)
        sendIntent.type = "text/plain"
        context.startActivity(Intent.createChooser(sendIntent, "Share using..."))
    }

    fun nextApp(context: Context) {
        val itemModel = ServerPrefs(context).getItemModel()
        if (itemModel?.more_app.isNullOrEmpty()) {
            rateApp(context)
        } else {
            Log.d("LOG", "Open URL " + itemModel.more_app)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(itemModel.more_app))
            context.startActivity(intent)
        }
    }

}