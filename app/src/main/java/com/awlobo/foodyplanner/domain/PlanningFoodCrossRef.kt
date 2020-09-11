package com.awlobo.foodyplanner.domain

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity(primaryKeys = [Planning.ID_NAME, Food.ID_NAME])
data class PlanningFoodCrossRef(
    val planningId: Int,
    val foodId: Int
)