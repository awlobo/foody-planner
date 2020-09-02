package com.awlobo.foodyplanner

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val viewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        viewModel.loadFoods()
        viewModel.loadPlanning()
    }

    private fun createFoodDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.add_food))
        val viewInflated: View = LayoutInflater.from(this)
            .inflate(R.layout.text_input_dialog, findViewById(android.R.id.content), false)
        val input: EditText = viewInflated.findViewById(R.id.editTextTextPersonName)
        builder.setView(viewInflated)

        builder.setPositiveButton(
            android.R.string.ok
        ) { dialog, _ ->
            viewModel.newFoodLiveData.value = Comida(input.text.toString())
            dialog.dismiss()
        }

        builder.setNegativeButton(
            android.R.string.cancel
        ) { dialog, _ ->
            dialog.cancel()
        }

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
            R.id.action_add_food -> {
                createFoodDialog();true
            }
            R.id.action_clear_table -> {
                viewModel.deleteData.value = true; true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}