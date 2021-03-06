package com.awlobo.foodyplanner.data.domain.planning.crossref

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.awlobo.foodyplanner.data.domain.food.FoodModel
import com.awlobo.foodyplanner.data.domain.planning.Planning

data class PlanningWithFoods(
    @Embedded val planning: Planning,
    @Relation(
        parentColumn = Planning.ID_NAME,
        entityColumn = FoodModel.ID_NAME,
        associateBy = Junction(
            value = PlanningFoodCrossRef::class,
            parentColumn = Planning.ID_NAME,
            entityColumn = FoodModel.ID_NAME
        ),
        entity = FoodModel::class
    )
    val foodList: List<FoodModel>
//    val foodList: Array<Food?> = arrayOfNulls(14)
)