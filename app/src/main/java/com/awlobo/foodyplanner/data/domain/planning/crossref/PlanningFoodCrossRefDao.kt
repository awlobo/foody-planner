package com.awlobo.foodyplanner.data.domain.planning.crossref

import androidx.lifecycle.LiveData
import androidx.room.*
import com.awlobo.foodyplanner.data.domain.food.Food

@Dao
interface PlanningFoodCrossRefDao {

    //    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(join: PlanningFoodCrossRef)

    @Delete()
    suspend fun remove(join: PlanningFoodCrossRef)

    @Transaction
    @Query("SELECT * FROM plannings")
    fun getAll(): List<PlanningWithFoods>

    @Transaction
    @Query("SELECT foods.name, pos, plannings.planningId FROM plannings, foods, PlanningFoodCrossRef WHERE plannings.planningId = 1 AND PlanningFoodCrossRef.foodId = foods.foodId AND PlanningFoodCrossRef.planningId = plannings.planningId")
    fun get(): LiveData<List<Food>>
}