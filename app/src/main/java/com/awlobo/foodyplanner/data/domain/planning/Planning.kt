package com.awlobo.foodyplanner.data.domain.planning

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Planning.TABLE_NAME)
data class Planning(
    @PrimaryKey(autoGenerate = true) val planningId: Long = 0,
    val name: String = "plan_$planningId",
) {
    companion object {
        const val TABLE_NAME = "plannings"
        const val ID_NAME = "planningId"
    }
}