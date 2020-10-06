package com.awlobo.foodyplanner

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.awlobo.foodyplanner.core.ScreenshotHelper
import com.awlobo.foodyplanner.data.domain.food.Food
import java.io.File
import java.util.*

const val SHARED_INTENT_ID = 123
const val FILE_NAME = "my_planning.png"

class MainActivity : AppCompatActivity() {

    private val viewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
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
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_screenshot -> {
                ScreenshotHelper(this).makeScreenshot(); true
            }
            R.id.action_add_food -> {
                createFoodDialog();true
            }
            R.id.action_clear_table -> {
                viewModel.deleteAllData.value = true; true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            SHARED_INTENT_ID -> {
                (data?.getSerializableExtra("file") as File?)?.delete()
            }
        }
    }

}