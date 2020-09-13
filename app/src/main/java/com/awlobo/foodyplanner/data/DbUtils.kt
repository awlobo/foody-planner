package com.awlobo.foodyplanner.data

import com.awlobo.foodyplanner.data.domain.food.Food
import com.awlobo.foodyplanner.data.domain.food.FoodDao
import com.awlobo.foodyplanner.data.domain.planning.Planning
import com.awlobo.foodyplanner.data.domain.planning.PlanningDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun rePopulateDb(database: AppDatabase?) {
    database?.let { db ->
        withContext(Dispatchers.IO) {
            val planningDao: PlanningDao = db.planningDao()
            val foodDao: FoodDao = db.foodDao()

            foodDao.insert(Food(""))
            foodDao.insert(Food("Tortilla de patatas"))
            planningDao.insert(Planning())
//            val planningFoods =
//                PlanningFoodCrossRef(planningDao.insert(planning), foodDao.insert(foodOne), 3)
//            planWithFoods.insert(planningFoods)
        }
    }
}
