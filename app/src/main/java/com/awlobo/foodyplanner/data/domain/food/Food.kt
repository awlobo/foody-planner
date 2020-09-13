package com.awlobo.foodyplanner.data.domain.food

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Food.TABLE_NAME)
data class Food(
    val name: String,
    @PrimaryKey(autoGenerate = true) var foodId: Long = 0
) {
    companion object {
        const val TABLE_NAME = "foods"
        const val ID_NAME = "foodId"
    }
}