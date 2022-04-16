package com.pasuh.masterchef.model.dao.dao_sales_transaction_history.floor2

import androidx.room.Dao
import androidx.room.Query
import com.pasuh.masterchef.model.orm.SalesTransactionHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface SalesTransactionDetailDao {
    @Query("SELECT A.id,B.id AS id_menu,B.name,A.quantity,A.tgl,A.jam FROM salestransaction AS A INNER JOIN menufood AS B ON A.id_menu=B.id WHERE A.id = :id LIMIT 1")
    fun getTransaksi(id: Int): Flow<SalesTransactionHistory>
}