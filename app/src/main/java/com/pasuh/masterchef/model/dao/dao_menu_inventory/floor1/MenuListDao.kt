package com.pasuh.masterchef.model.dao.dao_menu_inventory.floor1

import androidx.room.Dao
import androidx.room.Query
import com.pasuh.masterchef.model.erd.MenuFood
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuListDao {
    @Query("SELECT * FROM menufood ORDER BY id DESC")
    fun getItems(): Flow<List<MenuFood>>
}