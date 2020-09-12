package com.awlobo.foodyplanner.data.domain.planning

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.awlobo.foodyplanner.data.domain.food.Food

data class PlanningWithFoods(
    @Embedded val planning: Planning,
    @Relation(
        parentColumn = Planning.ID_NAME,
        entityColumn = Food.ID_NAME,
        associateBy = Junction(
            value=PlanningFoodCrossRef::class,
            parentColumn = Planning.ID_NAME,
            entityColumn = Food.ID_NAME
            ),
        entity = Food::class
    )
    val foodList: List<Food>
//    val foodList: Array<Food?> = arrayOfNulls(14)
)