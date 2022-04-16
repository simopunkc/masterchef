package com.pasuh.masterchef.model.dao.dao_ingredient_inventory.floor2

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import com.pasuh.masterchef.model.erd.Ingredient
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDetailDao {
    @Delete
    suspend fun deleteRecipe(ingredient: Ingredient)

    @Transaction @Query("SELECT * FROM ingredient WHERE id = :id LIMIT 1")
    fun getRecipe(id: Int): Flow<Ingredient>
}
