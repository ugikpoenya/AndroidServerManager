package com.ugikpoenya.servermanager.tools

interface DownloadCallback {
    fun onProgressUpdate(progress: Int)
    fun onDownloadComplete(filePath: String)
    fun onDownloadFailed(errorMessage: String)
}