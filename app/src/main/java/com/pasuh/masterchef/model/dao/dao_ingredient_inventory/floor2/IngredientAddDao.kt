package com.pasuh.masterchef.model.dao.dao_ingredient_inventory.floor2

import androidx.room.*
import com.pasuh.masterchef.model.erd.Ingredient

@Dao
interface IngredientAddDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipe(ingredient: Ingredient)

    @Update
    suspend fun updateRecipe(ingredient: Ingredient)
    @Transaction
    suspend fun transactionUpdateRecipe(ingredient: Ingredient){
        updateRecipe(ingredient)
    }
}