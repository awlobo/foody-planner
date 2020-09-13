package com.awlobo.foodyplanner.core

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.view.PixelCopy
import android.view.View
import android.widget.Toast
import androidx.core.content.FileProvider
import com.awlobo.foodyplanner.FILE_NAME
import com.awlobo.foodyplanner.R
import com.awlobo.foodyplanner.SHARED_INTENT_ID
import java.io.File
import java.io.FileOutputStream

class ScreenshotHelper(val context: Context) {
    fun makeScreenshot() {
        val rootView =
            (context as Activity).window.decorView.findViewById<View>(R.id.nav_host_fragment)
        val table = rootView.findViewById<View>(R.id.table)

        lateinit var bitmap: Bitmap

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getScreenShotFromView(table, context) {
                bitmap = it
                shareImage(storeScreenShot(bitmap))
            }
        } else {
            bitmap = getScreenShot(table)
            shareImage(storeScreenShot(bitmap))
        }
    }

    private fun shareImage(file: File) {
        val uri = FileProvider.getUriForFile(
            context,
            context.packageName.toString() + ".provider",
            file
        )
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "image/*"
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra(Intent.EXTRA_SUBJECT, "")
        intent.putExtra(Intent.EXTRA_TEXT, "")
        intent.putExtra("file", file)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        try {
            (context as Activity).startActivityForResult(
                Intent.createChooser(intent, "Share Screenshot"),
                SHARED_INTENT_ID
            )
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(context, "No App Available", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getScreenShotFromView(view: View, activity: Activity, callback: (Bitmap) -> Unit) {
        activity.window?.let { window ->
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val locationOfViewInWindow = IntArray(2)
            view.getLocationInWindow(locationOfViewInWindow)
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    PixelCopy.request(
                        window,
                        Rect(
                            locationOfViewInWindow[0],
                            locationOfViewInWindow[1],
                            locationOfViewInWindow[0] + view.width,
                            locationOfViewInWindow[1] + view.height
                        ), bitmap, { copyResult ->
                            if (copyResult == PixelCopy.SUCCESS) {
                                callback(bitmap)
                            }
                        },
                        Handler()
                    )
                }
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }
    }

    //deprecated version
    private fun getScreenShot(view: View): Bitmap {
        val screenView = view.rootView
        screenView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(screenView.drawingCache)
        screenView.isDrawingCacheEnabled = false

        return bitmap
    }

    private fun storeScreenShot(bm: Bitmap?): File {
        val dir =
            File(context.getExternalFilesDir(null)!!.absolutePath)
        if (!dir.exists())
            dir.mkdirs()
        val file = File(dir.path, FILE_NAME)

        try {
            val fOut = FileOutputStream(file)
            bm?.compress(Bitmap.CompressFormat.JPEG, 10, fOut)
            fOut.flush()
            fOut.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file
    }
}