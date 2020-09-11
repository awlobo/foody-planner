package com.awlobo.foodyplanner.domain

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

data class PlanningWithFoods(
    @Embedded val planning: Planning,
    @Relation(
        parentColumn = Planning.ID_NAME,
        entityColumn = Food.ID_NAME,
        associateBy = Junction(PlanningFoodCrossRef::class)
    )
    val foodList: List<Food>
//    val foodList: Array<Food?> =arrayOfNulls<Food>(14)
)