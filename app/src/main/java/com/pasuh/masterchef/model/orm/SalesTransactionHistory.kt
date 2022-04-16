package com.pasuh.masterchef.model.orm

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class SalesTransactionHistory(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "id_menu")
    val idMenu: Int,
    @ColumnInfo(name = "name")
    val itemName: String,
    @ColumnInfo(name = "quantity")
    val quantityInTransaction: Int,
    @ColumnInfo(name = "tgl")
    val tglInserted: String,
    @ColumnInfo(name = "jam")
    val jamInserted: String
)