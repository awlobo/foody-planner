package com.awlobo.foodyplanner.data.domain.planning

import androidx.room.*

@Dao
interface PlanningFoodCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(join: PlanningFoodCrossRef)

    @Transaction
    @Query("SELECT * FROM plannings")
    fun getCourses(): List<PlanningWithFoods>
}