package com.ugikpoenya.servermanager.model

import java.io.Serializable


class ItemModel : Serializable {
    val DEFAULT_NATIVE_START = 2
    val DEFAULT_NATIVE_INTERVAL = 8
    val DEFAULT_PRIORITY = "0,1,2,3"

    //  Application Key


    // For Intersitial Setting
    val DEFAULT_INTERSTITIAL_DELAY = 0
    val DEFAULT_INTERSTITIAL_DELAY_FIRST = 0
    val DEFAULT_INTERSTITIAL_INTERVAL = 0
    var interstitial_delay: Int = DEFAULT_INTERSTITIAL_DELAY
    var interstitial_delay_first: Int = DEFAULT_INTERSTITIAL_DELAY_FIRST
    var interstitial_priority: String = DEFAULT_PRIORITY
    var interstitial_interval: Int = DEFAULT_INTERSTITIAL_INTERVAL
    var interstitial_interval_first: Int = 0
    var interstitial_interval_counter: Int = 0
    var interstitial_last_shown_time: Long = 0


    // For Open Ads
    val DEFAULT_OPENADS_DELAY = 0
    val DEFAULT_OPENADS_DELAY_FIRST = 0
    var open_ads_delay: Int = DEFAULT_OPENADS_DELAY
    var open_ads_delay_first: Int = DEFAULT_OPENADS_DELAY_FIRST
    var open_ads_last_shown_time: Long = 0

    // For Rewarded Ads
    val DEFAULT_REWARDED_ADS_DELAY = 0
    val DEFAULT_REWARDED_ADS_DELAY_FIRST = 0
    val DEFAULT_REWARDED_ADS_INTERVAL = 0
    var rewarded_ads_delay: Int = DEFAULT_REWARDED_ADS_DELAY
    var rewarded_ads_delay_first: Int = DEFAULT_REWARDED_ADS_DELAY_FIRST
    var rewarded_ads_priority: String = DEFAULT_PRIORITY
    var rewarded_ads_interval: Int = DEFAULT_REWARDED_ADS_INTERVAL
    var rewarded_ads_interval_first: Int = 0
    var rewarded_ads_interval_counter: Int = 0
    var rewarded_ads_last_shown_time: Long = 0


    var privacy_policy: String = ""
    var more_app: String = ""

    var native_start: Int = DEFAULT_NATIVE_START
    var native_interval: Int = DEFAULT_NATIVE_INTERVAL
    var native_view: String = ""
    var native_tab_position: String = "0,1,2"

    var asset_folder: String = ""
    var asset_url: String = ""
    var asset_storage: String = ""

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
    var applovin_merc: String = ""
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