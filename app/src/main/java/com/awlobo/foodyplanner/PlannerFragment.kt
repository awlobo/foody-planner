package com.awlobo.foodyplanner

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.planner_fragment.*
import kotlinx.android.synthetic.main.planner_fragment.view.*
import kotlinx.android.synthetic.main.table_days.view.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlannerFragment : Fragment(), View.OnDragListener, View.OnLongClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.planner_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: 1/09/20 cargar lista con chips, cuando dole click recuperar chip, comparar tag? 
        val tableLayout = table as ViewGroup

        val items = getViewsByTag(tableLayout, "item")
        items?.forEach { it.setOnDragListener(this) }

        tag1.tag = tag1.text
        tag2.tag = tag2.text
        tag1.setOnLongClickListener(this)
        tag2.setOnLongClickListener(this)

    }

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
            DragEvent.ACTION_DRAG_LOCATION ->                 // Ignore the event
                return true
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
                // Displays a message containing the dragged data.
                Toast.makeText(requireContext(), "Dragged data is $dragData", Toast.LENGTH_SHORT)
                    .show()
                // Turns off any color tints
                v!!.setBackgroundColor(Color.TRANSPARENT)
                // Invalidates the view to force a redraw
                v.invalidate()
                val vw = event.localState as Chip
                val owner = vw.parent as ViewGroup
                owner.removeView(vw) //remove the dragged view
                //caste the view into LinearLayout as our drag acceptable layout is LinearLayout
                val container = v as TextView
                container.text = vw.text //Add the dragged view
                vw.visibility = View.GONE //finally set Visibility to VISIBLE
                // Returns true. DragEvent.getResult() will return true.
                return true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                // Turns off any color tinting
                v!!.setBackgroundColor(Color.TRANSPARENT)
//                v!!.background.clearColorFilter()
                // Invalidates the view to force a redraw
                v.invalidate()
                // Does a getResult(), and displays what happened.
                if (event.result) Toast.makeText(
                    requireContext(),
                    "The drop was handled.",
                    Toast.LENGTH_SHORT
                ).show() else Toast.makeText(
                    requireContext(),
                    "The drop didn't work.",
                    Toast.LENGTH_SHORT
                )
                    .show()
                // returns true; the value is ignored.
                return true
            }
            else -> Log.e("DragDrop Example", "Unknown action type received by OnDragListener.")
        }
        return false
    }

    override fun onLongClick(v: View): Boolean {
        // Create a new ClipData.Item from the View object's tag
        val item = ClipData.Item(v.tag as CharSequence)

        // Create a new ClipData using the tag as a label, the plain text MIME type, and
        // the already-created item. This will create a new ClipDescription object within the
        // ClipData, and set its MIME type entry to "text/plain"
        val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
        val data = ClipData(v.tag.toString(), mimeTypes, item)

        // Instantiates the drag shadow builder.
        val dragshadow = DragShadowBuilder(v)

        // Starts the drag
        v.startDragAndDrop(
            data // data to be dragged
            , dragshadow // drag shadow
            , v // local data about the drag and drop operation
            , 0 // flags set to 0 because not using currently
        )
        return true
    }

    private fun getViewsByTag(root: ViewGroup, tag: String): ArrayList<View>? {
        val views = ArrayList<View>()
        val childCount = root.childCount
        for (i in 0 until childCount) {
            val child = root.getChildAt(i)
            if (child is ViewGroup) {
                views.addAll(getViewsByTag(child, tag)!!)
            }
            val tagObj = child.tag
            if (tagObj != null && tagObj.toString().contains(tag)) {
                views.add(child)
            }
        }
        return views
    }


}