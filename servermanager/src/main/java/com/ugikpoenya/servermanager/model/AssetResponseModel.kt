package com.ugikpoenya.servermanager.model

class AssetResponseModel {
    var status: Boolean? = null
    var files: ArrayList<String>? = null
    var folders: Map<String, ArrayList<String>>? = null
}