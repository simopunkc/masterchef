package com.pasuh.masterchef.model.orm

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class MaxProfit(
    @ColumnInfo(name = "menu")
    val menu: String,
    @ColumnInfo(name = "profit")
    val profit: Double
)