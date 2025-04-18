package com.ugikpoenya.servermanager.model

import java.io.Serializable

val DEFAULT_NATIVE_START = 2
val DEFAULT_NATIVE_INTERVAL = 8
val DEFAULT_INTERSTITIAL_INTERVAL = 0
var DEFAULT_PRIORITY = "0,1,2,3"

class ItemModel : Serializable {
    var more_app: String = ""
    var privacy_policy: String = ""
    var asset_folder: String = ""
    var asset_url: String = ""
    var asset_storage: String = ""

    var interstitial_interval: Int = DEFAULT_INTERSTITIAL_INTERVAL
    var native_start: Int = DEFAULT_NATIVE_START
    var native_interval: Int = DEFAULT_NATIVE_INTERVAL
    var interstitial_priority: String = DEFAULT_PRIORITY


    var home_banner: Boolean = true
    var home_native: Boolean = true
    var home_native_view: String = ""
    var home_priority: String = DEFAULT_PRIORITY

    var detail_banner: Boolean = true
    var detail_native: Boolean = true
    var detail_native_view: String = ""
    var detail_priority: String = DEFAULT_PRIORITY

    //Admob
    var admob_banner: String = ""
    var admob_interstitial: String = ""
    var admob_native: String = ""
    var admob_open_ads: String = ""
    var admob_rewarded_ads: String = ""
    var admob_gdpr: Boolean = false

    //Facebook Audience Network
    var facebook_banner: String = ""
    var facebook_interstitial: String = ""
    var facebook_native: String = ""
    var facebook_rewarded_ads: String = ""

    //Unity
    var unity_game_id: String = ""
    var unity_banner: String = ""
    var unity_interstitial: String = ""
    var unity_rewarded_ads: String = ""
    var unity_test_mode: Boolean = false

    //AppLovin
    var applovin_sdk_key: String = ""
    var applovin_banner: String = ""
    var applovin_interstitial: String = ""
    var applovin_native: String = ""
    var applovin_rewarded_ads: String = ""
    var applovin_open_ads: String = ""

    //Redirect
    var redirect_content: String =
        "Please update the application to the latest version to get additional features."
    var redirect_title: String = "Update"
    var redirect_button: String = "Update"
    var redirect_image_url: String = ""
    var redirect_url: String = ""
    var redirect_cancelable: Boolean = true

}