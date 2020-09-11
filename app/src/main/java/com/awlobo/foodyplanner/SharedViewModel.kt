package com.awlobo.foodyplanner

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.awlobo.foodyplanner.core.FirestoreHelper
import com.awlobo.foodyplanner.domain.*

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val foodDao = AppDatabase.getInstance(application)?.foodDao()
    private val repository = FoodRepository(application)
    val foodList:LiveData<List<Food>>?
    init {
        foodList = foodDao?.getAllFoods
    }


    suspend fun saveFood(vararg contact: Food) {
        foodDao?.insertAll(*contact)
    }

    val deleteData = MutableLiveData(false)
    val newFoodLiveData = MutableLiveData<Food>()
    val foodListLiveData = MutableLiveData<List<Food>>()
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