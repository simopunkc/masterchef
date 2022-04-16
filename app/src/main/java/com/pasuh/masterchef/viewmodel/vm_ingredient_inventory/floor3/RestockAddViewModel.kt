package com.pasuh.masterchef.viewmodel.vm_ingredient_inventory.floor3

import androidx.lifecycle.*
import com.pasuh.masterchef.model.dao.dao_ingredient_inventory.floor2.IngredientAddDao
import com.pasuh.masterchef.model.dao.dao_ingredient_inventory.floor2.IngredientDetailDao
import com.pasuh.masterchef.model.dao.dao_ingredient_inventory.floor3.RestockAddDao
import com.pasuh.masterchef.model.erd.Ingredient
import com.pasuh.masterchef.model.erd.Restock
import kotlinx.coroutines.launch
import java.text.DateFormat.getDateInstance
import java.text.DateFormat.getTimeInstance
import java.util.*

class RestockAddViewModel(
    private val itemDao: RestockAddDao,
    private val ingredientAddDao: IngredientAddDao,
    private val ingredientDetailDao: IngredientDetailDao
    ) : ViewModel() {

    fun isIngredientValid(ingredientQuantity: String): Boolean {
        if (ingredientQuantity.isBlank()) {
            return false
        }
        return true
    }

    fun retrieveRecipe(id: Int): LiveData<Ingredient> {
        return ingredientDetailDao.getRecipe(id).asLiveData()
    }

    fun addNewRestock(ingredient: Ingredient, quantity: Int) {
        viewModelScope.launch {
            itemDao.insertRestock(
                Restock(
                    idRecipe = ingredient.id,
                    quantityInTransaction = quantity,
                    tglInserted = getDateInstance().format(Date()),
                    jamInserted = getTimeInstance().format(Date())
                )
            )
        }
    }

    private fun getUpdatedRecipeEntry(
        recipeId: Int,
        recipeName: String,
        recipeCount: String
    ): Ingredient {
        return Ingredient(
            id = recipeId,
            recipeName = recipeName,
            quantityInStock = recipeCount.toInt()
        )
    }

    private fun transaksiUpdateRecipe(ingredient: Ingredient) {
        viewModelScope.launch {
            ingredientAddDao.transactionUpdateRecipe(ingredient)
        }
    }

    fun updateRecipeTransaction(
        recipeId: Int,
        recipeName: String,
        recipeCount: String
    ) {
        val updatedRecipe = getUpdatedRecipeEntry(recipeId, recipeName, recipeCount)
        transaksiUpdateRecipe(updatedRecipe)
    }
}

class RestockAddViewModelFactory(
    private val itemDao: RestockAddDao,
    private val ingredientAddDao: IngredientAddDao,
    private val ingredientDetailDao: IngredientDetailDao
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestockAddViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestockAddViewModel(itemDao, ingredientAddDao, ingredientDetailDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}