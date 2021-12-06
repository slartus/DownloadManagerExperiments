package ru.slartus.downloadmanagerexperiments

import android.app.DownloadManager
import android.app.IntentService
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        systemDownload(
            this,
            "updateinfo.json",
            "https://raw.githubusercontent.com/slartus/4pdaClient-plus/master/updateinfo.json"
        )
    }

    private fun systemDownload(context: Context, fileName: String, url: String) {
        val dm = (context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager)
        val request = DownloadManager.Request(Uri.parse(url))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        request.allowScanningByMediaScanner()
        dm.enqueue(request)
    }
}