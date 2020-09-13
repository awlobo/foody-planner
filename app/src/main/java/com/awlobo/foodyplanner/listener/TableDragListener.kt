package com.awlobo.foodyplanner.listener

import android.content.ClipDescription
import android.graphics.Color
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.TextView
import com.awlobo.foodyplanner.SharedViewModel
import kotlin.math.roundToInt

class TableDragListener(scrollDistance: Int, scrollView: ScrollView, viewModel: SharedViewModel) :
    View.OnDragListener {

    private val mScrollDistance: Int = scrollDistance
    private val mScrollView: ScrollView = scrollView
    private val mViewModel: SharedViewModel = viewModel

    override fun onDrag(v: View?, event: DragEvent): Boolean {
        // Defines a variable to store the action type for the incoming event
        // Handles each of the expected events
        when (event.action) {
            DragEvent.ACTION_DRAG_STARTED -> {
                // Determines if this View can accept the dragged data
                return event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                // Returns false. During the current drag and drop operation, this View will
                // not receive events again until ACTION_DRAG_ENDED is sent.
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                // Applies a GRAY or any color tint to the View. Return true; the return value is ignored.
                v!!.setBackgroundColor(Color.LTGRAY)
                // Invalidate the view to force a redraw in the new tint
                v.invalidate()
                return true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {

                val y = event.y.roundToInt()
                val translatedY = y - mScrollDistance
                val threshold = 50
                // make a scrolling up due the y has passed the threshold
                if (translatedY < threshold) {
                    // make a scroll up by 30 px
                    mScrollView.scrollBy(0, -30)
                }
                // make a autoscrolling down due y has passed the 500 px border
                if (translatedY + threshold > 500) {
                    // make a scroll down by 30 px
                    mScrollView.scrollBy(0, 30)
                }

                return true
            }

            DragEvent.ACTION_DRAG_EXITED -> {
                // Re-sets the color tint to blue. Returns true; the return value is ignored.
                // view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                //It will clear a color filter .
//                v!!.background.clearColorFilter()
                v!!.setBackgroundColor(Color.TRANSPARENT)
                // Invalidate the view to force a redraw in the new tint
                v.invalidate()
                return true
            }
            DragEvent.ACTION_DROP -> {
                // Gets the item containing the dragged data
                val item = event.clipData.getItemAt(0)
                // Gets the text data from the item.
                val dragData = item.text.toString()
                // Turns off any color tints
                v!!.setBackgroundColor(Color.TRANSPARENT)
                // Invalidates the view to force a redraw
                v.invalidate()
                val vw = event.localState as TextView
                val owner = vw.parent as ViewGroup
                owner.removeView(vw) //remove the dragged view
                //caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                val container = v as TextView
                container.text = dragData.split("-")[1] //Add the dragged view
//                savePlanning()
                mViewModel.addedToPlan.postValue(true)

//                vw.visibility = View.GONE //finally set Visibility to VISIBLE
                // Returns true. DragEvent.getResult() will return true.
                return true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                v!!.setBackgroundColor(Color.TRANSPARENT)
                v.invalidate()
                // if (!event.result)
//                mViewModel.deleteData.value = false
                return true
            }
            else -> Log.e("DragDrop Example", "Unknown action type received by OnDragListener.")
        }
        return false
    }


}