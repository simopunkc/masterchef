package com.pasuh.masterchef.model.dao.dao_restock_history.floor2

import androidx.room.Dao
import androidx.room.Query
import com.pasuh.masterchef.model.orm.RestockHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface RestockDetailDao {
    @Query("SELECT D.id,C.id AS id_recipe,C.name,D.quantity,D.tgl,D.jam FROM restock AS D INNER JOIN ingredient AS C ON D.id_recipe=C.id WHERE D.id = :id LIMIT 1")
    fun getRestock(id: Int): Flow<RestockHistory>
}
