package com.awlobo.foodyplanner.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Food.TABLE_NAME)
data class Food(
    val name: String = "",
    @PrimaryKey(autoGenerate = true) val foodId: Int = 0
) {
    companion object {
        const val TABLE_NAME = "foods"
        const val ID_NAME = "foodId"
    }
}