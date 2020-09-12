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
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.awlobo.foodyplanner.core.setBaseAdapter
import com.awlobo.foodyplanner.data.domain.food.Food
import com.awlobo.foodyplanner.data.domain.planning.Planning
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.item_food.view.*
import kotlinx.android.synthetic.main.planner_fragment.*
import java.util.*
import kotlin.math.roundToInt


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlannerFragment : Fragment(), View.OnDragListener, View.OnLongClickListener {

    private val viewModel: SharedViewModel by activityViewModels()

    var foodList = mutableListOf<Food>()
    val planning = Planning()

    private var mScrollDistance = 0
    lateinit var tableItems: ArrayList<View>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.planner_fragment, container, false)
    }

    override fun onStop() {
        savePlanning()
        super.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        val tableLayout = table as ViewGroup
        tableItems = getViewsByTag(tableLayout, "item")
        tableItems.forEach {
            it.setOnDragListener(this)
            (it as TextView).freezesText = true
        }

        scroll_view.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            mScrollDistance = scrollY
        }

        viewModel.getFoodList()?.observe(viewLifecycleOwner, {
            foodList.clear()
            foodList.addAll(it)
            rvComidas.adapter?.notifyDataSetChanged()
        })

        viewModel.deleteData.observe(viewLifecycleOwner, {
            if (it) {
                viewModel.deleteData.value = false
                tableItems.forEach { view ->
                    (view as TextView).text = ""
                }
            }
        })

        viewModel.getPlanningList()?.observe(viewLifecycleOwner, {
            it.forEach { item -> (tableItems[item.pos] as TextView).text = item.name ?: "" }
        })
    }

    private fun setAdapter() {
        rvComidas.setBaseAdapter(foodList, R.layout.item_food) {
            itemView.tvFoodName.text = it.name
            itemView.tvFoodName.tag = itemView.tvFoodName.text
            itemView.tvFoodName.setOnLongClickListener(this@PlannerFragment)
        }

        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        rvComidas.layoutManager = layoutManager
    }

    // TODO: 13/9/20 SALVAR: POSICION + ID FOOD + ID PLANNING -> CROSS-REF
    private fun savePlanning() {
//        tableItems.forEachIndexed { index, view ->
//            val key = "${index}_"
//            val tempFood = Food(name=(view as TextView).text.toString())
//            if ( (!planning.foodList.containsKey(key) || planning.foodList[key] != tempFood)
//            ) {
//                planning.foodList[key] = tempFood
//                FirestoreHelper().addPlanning(planning)
//            }
//        }
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
            DragEvent.ACTION_DRAG_LOCATION -> {

                val y = event.y.roundToInt()
                val translatedY = y - mScrollDistance
                val threshold = 50
                // make a scrolling up due the y has passed the threshold
                if (translatedY < threshold) {
                    // make a scroll up by 30 px
                    scroll_view.scrollBy(0, -30)
                }
                // make a autoscrolling down due y has passed the 500 px border
                if (translatedY + threshold > 500) {
                    // make a scroll down by 30 px
                    scroll_view.scrollBy(0, 30)
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
                container.text = dragData //Add the dragged view
//                savePlanning()

//                vw.visibility = View.GONE //finally set Visibility to VISIBLE
                // Returns true. DragEvent.getResult() will return true.
                return true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                v!!.setBackgroundColor(Color.TRANSPARENT)
                v.invalidate()
                // if (!event.result)
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
        val dragShadow = DragShadowBuilder(v)

        // Starts the drag
        v.startDragAndDrop(
            data // data to be dragged
            , dragShadow // drag shadow
            , v // local data about the drag and drop operation
            , 0 // flags set to 0 because not using currently
        )
        return true
    }

    private fun getViewsByTag(root: ViewGroup, tag: String): ArrayList<View> {
        val views = ArrayList<View>()
        val childCount = root.childCount
        for (i in 0 until childCount) {
            val child = root.getChildAt(i)
            if (child is ViewGroup) {
                views.addAll(getViewsByTag(child, tag))
            }
            val tagObj = child.tag
            if (tagObj != null && tagObj.toString().contains(tag)) {
                views.add(child)
            }
        }
        return views
    }
}