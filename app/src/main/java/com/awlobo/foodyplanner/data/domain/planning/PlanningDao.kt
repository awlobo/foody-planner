package com.awlobo.foodyplanner.data.domain.planning

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.awlobo.foodyplanner.data.domain.planning.crossref.PlanningWithFoods


@Dao
interface PlanningDao {

//    fun insert(recipeWithIngredients: PlanningWithFoods) {
//        val id = insertRecipe(recipeWithIngredients.planning)
//        recipeWithIngredients.foodList.forEach { i -> i.foodId = id }
//        insertAll(recipeWithIngredients.foodList)
//    }

//    fun delete(recipeWithIngredients: PlanningWithFoods) {
//        delete(recipeWithIngredients.planning, recipeWithIngredients.foodList)
//    }
//
//    @Insert
//    fun insertAll(ingredients: List<Food?>?)

//    @Insert
//    fun insertRecipe(recipe: Planning?): Array<Int>

//
//    @Transaction
//    @Delete
//    fun delete(recipe: Planning?, ingredients: List<Food?>?)
//
//    @Delete
//    fun delete(planning: Planning)
//
//    @Transaction
//    @Query("SELECT * from plannings")
//    fun getAll(): LiveData<List<PlanningWithFoods>>

//    @Insert
//    fun insert(planningWithFood: PlanningFoodCrossRef)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertSecond(planningWithFood: PlanningWithFoods)

    @Query("SELECT * FROM plannings")
    fun getAll(): LiveData<List<PlanningWithFoods>>

    @Insert
    fun insert(planning: Planning):Long

}