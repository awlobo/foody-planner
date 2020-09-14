package com.awlobo.foodyplanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.awlobo.foodyplanner.core.setBaseAdapter
import com.awlobo.foodyplanner.data.domain.food.Food
import com.awlobo.foodyplanner.data.domain.food.FoodTable
import com.awlobo.foodyplanner.data.domain.planning.crossref.PlanningFoodCrossRef
import com.awlobo.foodyplanner.listener.DeleteDragListener
import com.awlobo.foodyplanner.listener.ItemLongClickListener
import com.awlobo.foodyplanner.listener.TableDragListener
import com.awlobo.foodyplanner.listener.TableLongClickListener
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.android.synthetic.main.item_food.view.*
import kotlinx.android.synthetic.main.planner_fragment.*
import java.util.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PlannerFragment : Fragment() {

    private val viewModel: SharedViewModel by activityViewModels()

    private var foodList = mutableListOf<Food>()
    private var planningList = mutableListOf<FoodTable>()

    private val listIdTemp = LongArray(14)

    private var mScrollDistance = 0
    private lateinit var tableItems: ArrayList<View>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.planner_fragment, container, false)
    }

    override fun onPause() {
        saveDatabase()
        super.onPause()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()

        val tableLayout = table as ViewGroup
        tableItems = getViewsByTag(tableLayout, "item")
        tableItems.forEach {
            it.setOnDragListener(TableDragListener(mScrollDistance, scroll_view, viewModel))
            it.setOnLongClickListener(TableLongClickListener(viewModel))
            (it as TextView).freezesText = true
        }
        delete_food.setOnDragListener(DeleteDragListener(requireContext(), viewModel))
        scroll_view.setOnScrollChangeListener { _, _, scrollY, _, _ -> mScrollDistance = scrollY }

        viewModel.getFoodList()?.observe(viewLifecycleOwner, { list ->
            foodList.clear().also { foodList.addAll(list.filter { it.foodId != 1L }) }
            rvComidas.adapter?.notifyDataSetChanged()
        })

        viewModel.deleteAllData.observe(viewLifecycleOwner, {
            if (it) {
                viewModel.deleteAllData.value = false
                tableItems.forEach { view -> (view as TextView).text = "" }
                savePlanning()
            }
        })
        viewModel.deleteButtonState.observe(viewLifecycleOwner, {
            delete_food.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.addedToPlan.observe(viewLifecycleOwner, {
            if (it) savePlanning(); viewModel.addedToPlan.postValue(false)
        })

        viewModel.getPlanningList()?.observe(viewLifecycleOwner, { list ->
            planningList.clear().also { planningList.addAll(list) }
            list.forEach { item -> (tableItems[item.pos] as TextView).text = item.name }
        })
    }

    private fun saveDatabase() {
        if (listIdTemp.all { it != 0L }) {
            listIdTemp.forEachIndexed { index, foodId ->
                viewModel.insertToPlanning(
                    PlanningFoodCrossRef(1, foodId, index)
                )
            }
        }
    }

    private fun setAdapter() {
        rvComidas.setBaseAdapter(foodList, R.layout.item_food) {
            itemView.tvFoodName.text = it.name
            itemView.tvFoodName.tag = it.foodId
            itemView.tvFoodName.setOnLongClickListener(ItemLongClickListener(viewModel))
        }

        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.justifyContent = JustifyContent.CENTER
        rvComidas.layoutManager = layoutManager
    }

    private fun savePlanning() {
        tableItems.forEachIndexed { index, view ->
            val text = (view as TextView).text.toString()
            listIdTemp[index] = foodList.find { it.name == text }?.foodId ?: 1
        }
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