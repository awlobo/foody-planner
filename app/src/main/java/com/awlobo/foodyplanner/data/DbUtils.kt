package com.awlobo.foodyplanner.data

import com.awlobo.foodyplanner.domain.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun rePopulateDb(database: AppDatabase?) {
    database?.let { db ->
        withContext(Dispatchers.IO) {
            val planningDao: PlanningDao = db.planningDao()
            val foodDao: FoodDao = db.foodDao()
//            val planWithFoods: PlanningWithFoodsDao = db.planningWithFoods()

//            planningDao.deleteAll()
//            foodDao.deleteAll()

            val foodOne = Food("Tortilla de patatas")
            val foodTwo = Food("Macarrones")
            val fooThree = Food("Puerros")

            foodDao.insertAll(foodOne, foodTwo, fooThree)

            val planning = Planning()

//            planningDao.insertAll(planning)

            val planningFoods = PlanningWithFoods(planning, listOf(foodOne, foodTwo, fooThree))

//            planningDao.insert(planningFoods)


//            val planWithFood = PlanningWithFoods()
//            planWithFood.foodList[3]= foodOne
//            planWithFood.foodList[5]= foodTwo
//            planWithFood.foodList[1]= fooThree

//            planWithFoods.insert(planWithFood)

        }
    }
}
