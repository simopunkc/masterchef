package com.pasuh.masterchef.model.dao.dao_restock_history.floor1

import androidx.room.Dao
import androidx.room.Query
import com.pasuh.masterchef.model.orm.RestockHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface RestockListDao {
    @Query("SELECT D.id,C.id AS id_recipe,C.name,D.quantity,D.tgl,D.jam FROM restock AS D INNER JOIN ingredient AS C ON D.id_recipe=C.id ORDER BY D.id DESC")
    fun getRestocks(): Flow<List<RestockHistory>>
}