package com.awlobo.foodyplanner.data.domain.planning

import androidx.room.Entity
import com.awlobo.foodyplanner.data.domain.food.Food


@Entity(primaryKeys = [Planning.ID_NAME, Food.ID_NAME])
data class PlanningFoodCrossRef(
    val planningId: Long,
    val foodId: Long,
    val pos: Int
)