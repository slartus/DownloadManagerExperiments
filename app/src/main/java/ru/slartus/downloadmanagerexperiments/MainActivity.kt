package ru.slartus.downloadmanagerexperiments

import android.Manifest
import android.app.DownloadManager

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                download()
            } else {
                Toast.makeText(this, "Permission is not granted", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        when {
            Build.VERSION.SDK_INT < 23 -> download()
            Build.VERSION.SDK_INT < 30 -> activityResultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            else -> download()
        }
    }

    private fun download() {
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