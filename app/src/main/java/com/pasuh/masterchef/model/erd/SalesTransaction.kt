package com.pasuh.masterchef.model.erd

import androidx.room.*

@Entity(
    indices = [
        Index("id_menu")
    ],
    foreignKeys = [
        ForeignKey(
            entity = MenuFood::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("id_menu"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SalesTransaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "id_menu")
    val idMenu: Int,
    @ColumnInfo(name = "quantity")
    val quantityInTransaction: Int,
    @ColumnInfo(name = "tgl")
    val tglInserted: String,
    @ColumnInfo(name = "jam")
    val jamInserted: String
)