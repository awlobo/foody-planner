package com.awlobo.foodyplanner.data.domain.planning

import android.app.Application
import androidx.lifecycle.LiveData
import com.awlobo.foodyplanner.data.AppDatabase

class PlaningRepository(application: Application) {

    private val planningDao: PlanningDao? = AppDatabase.getInstance(application)?.planningDao()

//    fun insertPlanning(planning: PlanningWithFoods) {
//        planningDao?.insert(planning)
//    }

//    suspend fun deletePlanning(food: Food) {
//        planningDao?.delete(food)
//    }

//    suspend fun getFoodById(foodId: Int) {
//        planningDao?.getById(foodId)
//    }

    fun getAllPlannings(): LiveData<List<PlanningWithFoods>>? {
        return planningDao?.getAll()
    }

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