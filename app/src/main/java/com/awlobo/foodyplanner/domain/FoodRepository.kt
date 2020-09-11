package com.awlobo.foodyplanner.domain

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.Flow

class FoodRepository(application: Application) {

    private val foodDao: FoodDao? = AppDatabase.getInstance(application)?.foodDao()
/*
    fun insert(food: Food) {
        if (foodDao != null) InsertAsyncTask(foodDao).execute(food)
    }*/

    /*fun getFoods(): LiveData<List<Food>>? {
        return foodDao?.getAllFoods()
    }*/

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