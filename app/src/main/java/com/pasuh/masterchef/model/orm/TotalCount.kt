package com.pasuh.masterchef.model.orm

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class TotalCount(
    @ColumnInfo(name = "total")
    val totalCount: Int
)