package com.awlobo.foodyplanner.data

import com.awlobo.foodyplanner.data.domain.food.Food
import com.awlobo.foodyplanner.data.domain.food.FoodDao
import com.awlobo.foodyplanner.data.domain.planning.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun rePopulateDb(database: AppDatabase?) {
    database?.let { db ->
        withContext(Dispatchers.IO) {
            val planningDao: PlanningDao = db.planningDao()
            val foodDao: FoodDao = db.foodDao()
            val planWithFoods: PlanningFoodCrossRefDao = db.planningFoodCrossRefDao()

//            planningDao.deleteAll()
//            foodDao.deleteAll()

            val foodOne = Food("Tortilla de patatas")
            val foodTwo = Food("Macarrones")
            val foodThree = Food("Puerros")
            val foodFour = Food("Pasta Fresca")

            val list: List<Food> = listOf(foodOne, foodTwo, foodThree, foodFour)
//            foodDao.insert(foodOne, foodTwo, foodThree, foodFour)

            val planning = Planning()

            val planningFoods = PlanningFoodCrossRef(planningDao.insert(planning), foodDao.insert(foodOne))
            planWithFoods.insert(planningFoods)
//            planningDao.insert(PlanningFoodCrossRef(planning.planningId, foodTwo.foodId))

        }
    }
}
