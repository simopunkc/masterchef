package com.pasuh.masterchef.model.orm

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class MenuIngredientSingle(
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "id_menu")
    val idMenu: Int,
    @ColumnInfo(name = "id_recipe")
    val idRecipe: Int,
    @ColumnInfo(name = "recipe_quantity")
    val recipeQuantity: Int,
    @ColumnInfo(name = "recipe_updated")
    val recipeUpdated: String,
    @ColumnInfo(name = "name")
    val recipeName: String,
    @ColumnInfo(name = "stock")
    val recipeStock: Int
)