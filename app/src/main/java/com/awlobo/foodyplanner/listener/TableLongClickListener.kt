package com.awlobo.foodyplanner.listener

import android.view.View
import android.widget.TextView
import com.awlobo.foodyplanner.SharedViewModel

class TableLongClickListener(private val viewModel: SharedViewModel) : View.OnLongClickListener {
    override fun onLongClick(v: View?): Boolean {
        val textView = v as TextView
        textView.text = ""
        viewModel.addedToPlan.value = true
        return true
    }
}