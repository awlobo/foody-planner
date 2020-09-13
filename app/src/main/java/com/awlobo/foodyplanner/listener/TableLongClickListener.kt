package com.awlobo.foodyplanner.listener

import android.view.View
import android.widget.TextView

class TableLongClickListener : View.OnLongClickListener {
    override fun onLongClick(v: View?): Boolean {
        val textView = v as TextView
        textView.text = ""
        return true
    }
}