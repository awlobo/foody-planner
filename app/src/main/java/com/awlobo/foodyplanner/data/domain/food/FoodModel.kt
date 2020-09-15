package com.awlobo.foodyplanner.data.domain.food

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = FoodModel.TABLE_NAME)
data class FoodModel(
    val name: String,
    @PrimaryKey(autoGenerate = true) var foodId: Long = 0
) {
    companion object {
        const val TABLE_NAME = "foods"
        const val ID_NAME = "foodId"
    }
}