package com.pasuh.masterchef.model.dao.dao_menu_inventory.floor4

import androidx.room.*
import com.pasuh.masterchef.model.erd.MenuIngredient
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuIngredientAddDao {
    @Query("SELECT * FROM menuingredient WHERE id = :id LIMIT 1")
    fun getIngredient(id: Int): Flow<MenuIngredient>

    @Update
    suspend fun updateIngredient(menuIngredient: MenuIngredient)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(menuIngredient: MenuIngredient)
}
