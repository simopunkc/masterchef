package com.pasuh.masterchef.model.dao.dao_menu_inventory.floor3

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.pasuh.masterchef.model.erd.MenuIngredient
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuIngredientDetailDao {
    @Delete
    suspend fun deleteIngredient(menuIngredient: MenuIngredient)

    @Query("SELECT * FROM menuingredient WHERE id = :id LIMIT 1")
    fun getIngredient(id: Int): Flow<MenuIngredient>
}
