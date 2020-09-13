package com.awlobo.foodyplanner.data.domain.planning.crossref

import androidx.room.Entity


@Entity(primaryKeys = ["pos"])
//@Entity(primaryKeys = [Planning.ID_NAME, Food.ID_NAME])
data class PlanningFoodCrossRef(
    val planningId: Long,
    val foodId: Long,
    val pos: Int
)