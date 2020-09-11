package com.awlobo.foodyplanner.domain

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanningDao {


    @Transaction
    @Query("SELECT * from plannings")
    fun getPlanningsWithFoods(): List<PlanningWithFoods>

//    @Transaction
//    @Insert
//    fun insert(planningWithFood: PlanningWithFoods)

    @Query("SELECT * FROM plannings")
    fun getAll(): Flow<List<Planning>>

    @Insert
    fun insertAll(vararg planning: Planning)

    @Delete
    fun delete(planning: Planning)
}