package com.pasuh.masterchef.model.orm

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class RestockHistory(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "id_recipe")
    val idRecipe: Int,
    @ColumnInfo(name = "name")
    val recipeName: String,
    @ColumnInfo(name = "quantity")
    val quantityInTransaction: Int,
    @ColumnInfo(name = "tgl")
    val tglInserted: String,
    @ColumnInfo(name = "jam")
    val jamInserted: String
)