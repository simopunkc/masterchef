package com.pasuh.masterchef.model.dao.dao_ingredient_inventory.floor3

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.pasuh.masterchef.model.erd.Restock

@Dao
interface RestockAddDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRestock(restock: Restock)
}
