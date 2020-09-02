package com.awlobo.foodyplanner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awlobo.foodyplanner.core.FirestoreHelper
import com.awlobo.foodyplanner.core.Planning

class SharedViewModel() : ViewModel() {

    val deleteData = MutableLiveData(false)
    val newFoodLiveData = MutableLiveData<Comida>()
    val foodListLiveData = MutableLiveData<List<Comida>>()
    val planningLiveData = MutableLiveData<MutableMap<Int, Comida>>()

    fun loadFoods() {
        FirestoreHelper().readFoodList().addSnapshotListener { value, e ->
            val foodListTemp: MutableList<Comida>? = value?.toObjects(Comida::class.java)
            if (foodListTemp != null) {
                foodListLiveData.postValue(foodListTemp)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun loadPlanning() {
        FirestoreHelper().readPlanning().addSnapshotListener { value, e ->
            val planningTemp = value?.toObjects(Planning::class.java)
            if (!planningTemp.isNullOrEmpty()) {
                val newMap: MutableMap<Int, Comida> = mutableMapOf()
                planningTemp[0].foodList.forEach { (key, value) ->
                    val k = key.split("_")[0].toInt()
                    newMap[k] = value
                }
                planningLiveData.postValue(newMap)
            }
        }
    }
}