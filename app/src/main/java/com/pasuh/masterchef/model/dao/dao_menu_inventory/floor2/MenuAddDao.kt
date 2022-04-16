package com.pasuh.masterchef.model.dao.dao_menu_inventory.floor2

import androidx.room.*
import com.pasuh.masterchef.model.erd.MenuFood
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuAddDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMenu(menuFood: MenuFood)

    @Update
    suspend fun updateMenu(menuFood: MenuFood)

    @Query("SELECT * FROM menufood WHERE id = :id LIMIT 1")
    fun getItem(id: Int): Flow<MenuFood>
}
