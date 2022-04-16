package com.pasuh.masterchef.model.dao.dao_ingredient_inventory.floor1

import androidx.room.Dao
import androidx.room.Query
import com.pasuh.masterchef.model.erd.Ingredient
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientListDao {
    @Query("SELECT * FROM ingredient ORDER BY id DESC")
    fun getRecipes(): Flow<List<Ingredient>>
}
