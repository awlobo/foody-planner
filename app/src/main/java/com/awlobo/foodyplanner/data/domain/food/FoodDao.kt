package com.awlobo.foodyplanner.data.domain.food

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FoodDao {

    @Query("SELECT * FROM foods ORDER BY name")
    fun getAll(): LiveData<List<Food>>

    @Query("SELECT * FROM foods WHERE foodId =:fid")
    suspend fun getById(fid: Long): Food

    @Query("SELECT * FROM foods WHERE name =:fName")
    suspend fun getByName(fName: String): Food

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(food: Food): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg foods: Food)

    @Delete
    suspend fun delete(food: Food)

    @Query("DELETE FROM foods WHERE foodId = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM foods")
    suspend fun deleteAll()
}