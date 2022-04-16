package com.pasuh.masterchef.model.erd

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat

@Entity
data class MenuFood(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val itemName: String,
    @ColumnInfo(name = "price")
    val itemPrice: Double
)

fun MenuFood.getFormattedPrice(): String =
    NumberFormat.getCurrencyInstance().format(itemPrice)