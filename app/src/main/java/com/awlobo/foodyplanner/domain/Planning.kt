package com.awlobo.foodyplanner.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = Planning.TABLE_NAME)
data class Planning(
    @PrimaryKey(autoGenerate = true) val planningId: Int = 0,
    val name: String = "plan_$planningId",
) {
    companion object {
        const val TABLE_NAME = "plannings"
        const val ID_NAME = "planningId"
    }
}