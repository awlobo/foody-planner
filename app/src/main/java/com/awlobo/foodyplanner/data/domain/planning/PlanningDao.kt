package com.awlobo.foodyplanner.data.domain.planning

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.awlobo.foodyplanner.data.domain.planning.crossref.PlanningWithFoods


@Dao
interface PlanningDao {

    @Query("SELECT * FROM plannings")
    fun getAll(): LiveData<List<PlanningWithFoods>>

    @Insert
    fun insert(planning: Planning):Long

}