package com.awlobo.foodyplanner

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.awlobo.foodyplanner.data.domain.food.Food
import java.io.File
import java.io.FileOutputStream
import java.util.*

const val SHARED_INTENT_ID = 123
const val FILE_NAME = "my_planning.png"

class MainActivity : AppCompatActivity() {

    private val viewModel: SharedViewModel by viewModels()
    private var tempFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

//        viewModel.loadFoods()
//        viewModel.foods
//        viewModel.loadPlanning()
    }

    private fun createFoodDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.add_food))
        val viewInflated: View = LayoutInflater.from(this)
            .inflate(R.layout.text_input_dialog, findViewById(android.R.id.content), false)
        val input: EditText = viewInflated.findViewById(R.id.etFoodName)
        builder.setView(viewInflated)

        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            viewModel.insertFood(Food(name = input.text.toString().capitalize(Locale.getDefault())))
            dialog.dismiss()
        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_screenshot -> {
                makeScreenshot()
                true
            }
            R.id.action_add_food -> {
                createFoodDialog();true
            }
            R.id.action_clear_table -> {
                viewModel.deleteData.value = true; true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareImage(file: File) {
        val uri = FileProvider.getUriForFile(
            this,
            applicationContext.packageName.toString() + ".provider",
            file
        )
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "image/*"
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.putExtra(Intent.EXTRA_SUBJECT, "")
        intent.putExtra(Intent.EXTRA_TEXT, "")
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        try {
            startActivityForResult(
                Intent.createChooser(intent, "Share Screenshot"),
                SHARED_INTENT_ID
            )
            tempFile = file
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SHARED_INTENT_ID -> {
                if (tempFile != null) {
                    tempFile!!.delete()
                    tempFile = null
                }
            }
        }
    }

    private fun makeScreenshot() {
        val rootView = window.decorView.findViewById<View>(R.id.nav_host_fragment)
        val table = rootView.findViewById<View>(R.id.table)

        lateinit var bitmap: Bitmap

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getScreenShotFromView(table, this@MainActivity) {
                bitmap = it
                shareImage(storeScreenShot(bitmap))
            }
        } else {
            bitmap = getScreenShot(table)
            shareImage(storeScreenShot(bitmap))
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
            File(getExternalFilesDir(null)!!.absolutePath)
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