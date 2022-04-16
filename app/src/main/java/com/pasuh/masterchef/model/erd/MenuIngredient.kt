package com.pasuh.masterchef.model.erd

import androidx.room.*

@Entity(
    indices = [
        Index("id_menu"),
        Index("id_recipe")
    ],
    foreignKeys = [
        ForeignKey(
            entity = MenuFood::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("id_menu"),
            onDelete = ForeignKey.CASCADE
        ), ForeignKey(
            entity = Ingredient::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("id_recipe"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MenuIngredient(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "id_menu")
    val idMenu: Int,
    @ColumnInfo(name = "id_recipe")
    val idRecipe: Int,
    @ColumnInfo(name = "recipe_quantity")
    val recipeQuantity: Int,
    @ColumnInfo(name = "recipe_updated")
    val recipeUpdated: String,
)