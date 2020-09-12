package com.awlobo.foodyplanner.data.domain.food

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FoodDao {

    @Query("SELECT * FROM foods ORDER BY name")
    fun getAll(): LiveData<List<Food>>

    @Query("SELECT * FROM foods WHERE foodId =:fid")
    suspend fun getById(fid: Long): Food

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(vararg foods: Food)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(food: Food):Long

    @Delete
    suspend fun delete(food: Food)
}