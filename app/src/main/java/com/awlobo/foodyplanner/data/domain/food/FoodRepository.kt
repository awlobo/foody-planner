package com.awlobo.foodyplanner.data.domain.food

import android.app.Application
import androidx.lifecycle.LiveData
import com.awlobo.foodyplanner.data.AppDatabase

class FoodRepository(application: Application) {

    private val foodDao: FoodDao? = AppDatabase.getInstance(application)?.foodDao()

    suspend fun insertFood(food: FoodModel) {
        foodDao?.insert(food)
    }

    suspend fun getFoodById(foodId: Long) {
        foodDao?.getById(foodId)
    }

    suspend fun getIdByFood(name: String): FoodModel? {
        return foodDao?.getByName(name)
    }

    fun getAllFoods(): LiveData<List<FoodModel>>? {
        return foodDao?.getAll()
    }

    suspend fun deleteFood(food: FoodModel) {
        foodDao?.delete(food)
    }

    suspend fun deleteFoodById(id: Int) {
        foodDao?.deleteById(id)
    }

    suspend fun deleteAllFoods() {
        foodDao?.deleteAll()
    }
}