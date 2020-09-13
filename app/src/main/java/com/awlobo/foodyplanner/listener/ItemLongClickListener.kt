package com.awlobo.foodyplanner.listener

import android.content.ClipData
import android.content.ClipDescription
import android.view.View
import android.widget.TextView
import com.awlobo.foodyplanner.SharedViewModel

class ItemLongClickListener(private val viewModel: SharedViewModel) : View.OnLongClickListener {
    override fun onLongClick(v: View?): Boolean {
        val textView = v as TextView
        val tvData = "${textView.tag}-${textView.text}"
        val item = ClipData.Item(tvData as CharSequence)
//        val item = ClipData.Item(textView.text as CharSequence)

        // Create a new ClipData using the tag as a label, the plain text MIME type, and
        // the already-created item. This will create a new ClipDescription object within the
        // ClipData, and set its MIME type entry to "text/plain"
        val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
        val data = ClipData(v.tag.toString(), mimeTypes, item)

        // Instantiates the drag shadow builder.
        val dragShadow = View.DragShadowBuilder(v)

        // Starts the drag
        v.startDragAndDrop(
            data // data to be dragged
            , dragShadow // drag shadow
            , v // local data about the drag and drop operation
            , 0 // flags set to 0 because not using currently
        )

        viewModel.deleteButtonState.value = true
        return true
    }
}