package com.pasuh.masterchef.model.dao.dao_sales_transaction_history.floor1

import androidx.room.Dao
import androidx.room.Query
import com.pasuh.masterchef.model.orm.SalesTransactionHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface SalesTransactionListDao {
    @Query("SELECT A.id,B.id AS id_menu,B.name,A.quantity,A.tgl,A.jam FROM salestransaction AS A INNER JOIN menufood AS B ON A.id_menu=B.id ORDER BY A.id DESC")
    fun getTransaksis(): Flow<List<SalesTransactionHistory>>
}