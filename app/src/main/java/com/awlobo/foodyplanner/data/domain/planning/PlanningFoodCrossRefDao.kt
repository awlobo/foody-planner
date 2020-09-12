package com.awlobo.foodyplanner.data.domain.planning

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PlanningFoodCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(join: PlanningFoodCrossRef)

    @Transaction
    @Query("SELECT * FROM plannings")
    fun getAll(): List<PlanningWithFoods>

    @Transaction
    @Query("SELECT foods.name, pos FROM plannings, foods, PlanningFoodCrossRef WHERE plannings.planningId = 1 AND PlanningFoodCrossRef.foodId = foods.foodId AND PlanningFoodCrossRef.planningId = plannings.planningId")
    fun get(): LiveData<List<PlanningWithFoodsPrueba>>
}