package com.awlobo.foodyplanner.data.domain.planning

import android.app.Application
import androidx.lifecycle.LiveData
import com.awlobo.foodyplanner.data.AppDatabase
import com.awlobo.foodyplanner.data.domain.food.Food
import com.awlobo.foodyplanner.data.domain.planning.crossref.PlanningFoodCrossRef
import com.awlobo.foodyplanner.data.domain.planning.crossref.PlanningFoodCrossRefDao

class PlaningRepository(application: Application) {

    private val planningDao: PlanningDao? = AppDatabase.getInstance(application)?.planningDao()
    private val crossPlanningDao: PlanningFoodCrossRefDao? =
        AppDatabase.getInstance(application)?.planningFoodCrossRefDao()

    suspend fun insert(join: PlanningFoodCrossRef) {
        crossPlanningDao?.insert(join)
    }

    fun getPlanning(): LiveData<List<Food>>? {
        return crossPlanningDao?.get()
    }

//    fun insertPlanning(planning: PlanningWithFoods) {
//        planningDao?.insert(planning)
//    }

//    suspend fun deletePlanning(food: Food) {
//        planningDao?.delete(food)
//    }

//    suspend fun getFoodById(foodId: Int) {
//        planningDao?.getById(foodId)
//    }


    /*private class InsertAsyncTask(private val foodDao: FoodDao) :
        AsyncTask<Food, Void, Void>() {
        override fun doInBackground(vararg foods: Food?): Void? {
            for (contact in foods) {
                if (contact != null) foodDao.insertAll(contact)
            }
            return null
        }
    }*/
}