package com.awlobo.foodyplanner.listener

import android.content.ClipDescription
import android.content.Context
import android.graphics.Color
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.awlobo.foodyplanner.R
import com.awlobo.foodyplanner.SharedViewModel

class DeleteDragListener(context: Context, viewModel: SharedViewModel) :
    View.OnDragListener {

    private val mViewModel = viewModel
    private val mContext = context

    override fun onDrag(v: View?, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                return event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                v!!.setBackgroundColor(mContext.resources.getColor(R.color.deleteLight, null))
                v.invalidate()
                return true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                return true
            }

            DragEvent.ACTION_DRAG_EXITED -> {
                v!!.setBackgroundColor(mContext.resources.getColor(R.color.delete, null))
                v.invalidate()
                return true
            }
            DragEvent.ACTION_DROP -> {
                val item = event.clipData.getItemAt(0)
                val dragData = item.text.toString()
                v!!.setBackgroundColor(mContext.resources.getColor(R.color.delete, null))
                v.invalidate()
                val vw = event.localState as TextView
                val owner = vw.parent as ViewGroup
                owner.removeView(vw)
                mViewModel.deleteFoodById(dragData.split("-")[0].toInt())
                mViewModel.deleteButtonState.value = false
                return true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                v!!.setBackgroundColor(Color.TRANSPARENT)
                v.invalidate()
                mViewModel.deleteButtonState.value = false
                return true
            }
        }
        return false
    }
}