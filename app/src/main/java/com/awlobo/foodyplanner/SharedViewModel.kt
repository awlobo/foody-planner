package com.awlobo.foodyplanner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.awlobo.foodyplanner.data.domain.food.Food
import com.awlobo.foodyplanner.data.domain.food.FoodRepository
import com.awlobo.foodyplanner.data.domain.planning.PlaningRepository
import com.awlobo.foodyplanner.data.domain.planning.PlanningWithFoods
import kotlinx.coroutines.launch

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val foodRepository = FoodRepository(application)
    private val planningRepository = PlaningRepository(application)

    /*------------------------- FOOD -------------------------*/
    fun getFoodList(): LiveData<List<Food>>? {
        return foodRepository.getAllFoods()
    }

    fun insertFood(food: Food) = viewModelScope.launch {
        foodRepository.insertFood(food)
    }

    fun deleteFood(food: Food) = viewModelScope.launch {
        foodRepository.deleteFood(food)
    }

    fun getFoodById(food: Food) = viewModelScope.launch {
        foodRepository.getFoodById(food.foodId)
    }


    /*--------------------- PLANNING -------------------------*/

    fun getPlanningList(): LiveData<List<PlanningWithFoods>>? {
        return planningRepository.getAllPlannings()
    }


    /*----------------------- SHARED -------------------------*/

    val deleteData = MutableLiveData(false)
    val newFoodLiveData = MutableLiveData<Food>()
    val planningLiveData = MutableLiveData<MutableMap<Int, Food>>()

//    fun loadPlanning() {
//        FirestoreHelper().readPlanning().addSnapshotListener { value, e ->
//            val planningTemp = value?.toObjects(Planning::class.java)
//            if (!planningTemp.isNullOrEmpty()) {
//                val newMap: MutableMap<Int, Food> = mutableMapOf()
//                planningTemp[0].foodList.forEach { (key, value) ->
//                    val k = key.split("_")[0].toInt()
//                    newMap[k] = value
//                }
//                planningLiveData.postValue(newMap)
//            }
//        }
//    }
}