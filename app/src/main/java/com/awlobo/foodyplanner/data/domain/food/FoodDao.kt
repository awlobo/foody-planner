package com.awlobo.foodyplanner.data.domain.food

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FoodDao {

    @Query("SELECT * FROM foods ORDER BY name")
    fun getAll(): LiveData<List<FoodModel>>

    @Query("SELECT * FROM foods WHERE foodId =:fid")
    suspend fun getById(fid: Long): FoodModel

    @Query("SELECT * FROM foods WHERE name =:fName")
    suspend fun getByName(fName: String): FoodModel

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(food: FoodModel): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg foods: FoodModel)

    @Delete
    suspend fun delete(food: FoodModel)

    @Query("DELETE FROM foods WHERE foodId = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM foods WHERE foodId != 1")
    suspend fun deleteAll()
}