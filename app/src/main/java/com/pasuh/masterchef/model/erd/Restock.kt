package com.pasuh.masterchef.model.erd

import androidx.room.*

@Entity(
    indices = [
        Index("id_recipe")
    ],
    foreignKeys = [
        ForeignKey(
            entity = Ingredient::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("id_recipe"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Restock(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "id_recipe")
    val idRecipe: Int,
    @ColumnInfo(name = "quantity")
    val quantityInTransaction: Int,
    @ColumnInfo(name = "tgl")
    val tglInserted: String,
    @ColumnInfo(name = "jam")
    val jamInserted: String
)