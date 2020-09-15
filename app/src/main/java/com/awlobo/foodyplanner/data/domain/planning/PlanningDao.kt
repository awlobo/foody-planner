package com.awlobo.foodyplanner.data.domain.planning

import androidx.room.Dao
import androidx.room.Insert


@Dao
interface PlanningDao {
    @Insert
    fun insert(planning: Planning): Long
}