package com.pasuh.masterchef.model.orm

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class MenuIngredient(
    @ColumnInfo(name = "id")
    val idMenu: Int,
    @ColumnInfo(name = "name")
    val menuName: String,
    @ColumnInfo(name = "price")
    val menuPrice: Double,
    @ColumnInfo(name = "id_recipe")
    val idRecipe: String,
    @ColumnInfo(name = "quantity")
    val recipeQuantity: String,
    @ColumnInfo(name = "stock")
    val stockQuantity: String
)

fun MenuIngredient.listIdRecipe(): List<Int> =
    idRecipe.split(",").map { it.toInt() }

fun MenuIngredient.listRecipeQuantity(): List<Int> =
    recipeQuantity.split(",").map { it.toInt() }

fun MenuIngredient.listStockQuantity(): List<Int> =
    stockQuantity.split(",").map { it.toInt() }