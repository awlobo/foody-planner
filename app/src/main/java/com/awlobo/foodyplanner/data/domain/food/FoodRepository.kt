package com.awlobo.foodyplanner.data.domain.food

import android.app.Application
import androidx.lifecycle.LiveData
import com.awlobo.foodyplanner.data.AppDatabase

class FoodRepository(application: Application) {

    private val foodDao: FoodDao? = AppDatabase.getInstance(application)?.foodDao()

    suspend fun insertFood(food: Food) {
        foodDao?.insert(food)
    }

    suspend fun deleteFood(food: Food) {
        foodDao?.delete(food)
    }

    suspend fun getFoodById(foodId: Long) {
        foodDao?.getById(foodId)
    }

    fun getAllFoods(): LiveData<List<Food>>? {
        return foodDao?.getAll()
    }
}