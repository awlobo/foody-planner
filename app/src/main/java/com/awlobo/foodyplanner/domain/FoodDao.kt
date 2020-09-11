package com.awlobo.foodyplanner.domain

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @get:Query("SELECT * FROM foods ORDER BY name")
    val getAllFoods: LiveData<List<Food>>

//    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>

    @Insert
    suspend fun insertAll(vararg foods: Food)

    @Delete
    suspend fun delete(user: Food)
}