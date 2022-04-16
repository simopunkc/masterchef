package com.pasuh.masterchef.model.erd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val recipeName: String,
    @ColumnInfo(name = "quantity")
    val quantityInStock: Int
)