package com.awlobo.foodyplanner.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.awlobo.foodyplanner.data.domain.food.Food
import com.awlobo.foodyplanner.data.domain.food.FoodDao
import com.awlobo.foodyplanner.data.domain.planning.Planning
import com.awlobo.foodyplanner.data.domain.planning.PlanningDao
import com.awlobo.foodyplanner.data.domain.planning.PlanningFoodCrossRef
import com.awlobo.foodyplanner.data.domain.planning.PlanningFoodCrossRefDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Food::class, Planning::class, PlanningFoodCrossRef::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun planningDao(): PlanningDao
    abstract fun  planningFoodCrossRefDao(): PlanningFoodCrossRefDao

    companion object {
        private const val DATABASE_NAME = "foody_planner_database.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                ).addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        GlobalScope.launch(Dispatchers.IO) { rePopulateDb(INSTANCE) }
                    }
                }).build()
            }
            return INSTANCE
        }
    }
}