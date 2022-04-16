package com.pasuh.masterchef.viewmodel.vm_ingredient_inventory.floor2

import androidx.lifecycle.*
import com.pasuh.masterchef.model.dao.dao_ingredient_inventory.floor2.IngredientAddDao
import com.pasuh.masterchef.model.dao.dao_ingredient_inventory.floor2.IngredientDetailDao
import com.pasuh.masterchef.model.erd.Ingredient
import kotlinx.coroutines.launch

class IngredientAddViewModel(private val itemDao: IngredientAddDao, private val ingredientDetailDao: IngredientDetailDao) : ViewModel() {

    private fun insertRecipe(ingredient: Ingredient) {
        viewModelScope.launch {
            itemDao.insertRecipe(ingredient)
        }
    }

    private fun getNewRecipeEntry(recipeName: String, recipeCount: String): Ingredient {
        return Ingredient(
            recipeName = recipeName,
            quantityInStock = recipeCount.toInt()
        )
    }

    fun addNewRecipe(recipeName: String, recipeCount: String) {
        val newRecipe = getNewRecipeEntry(recipeName, recipeCount)
        insertRecipe(newRecipe)
    }

    fun isRecipeValid(recipeName: String, recipeCount: String): Boolean {
        if (recipeName.isBlank() || recipeCount.isBlank()) {
            return false
        }
        return true
    }

    fun retrieveRecipe(id: Int): LiveData<Ingredient> {
        return ingredientDetailDao.getRecipe(id).asLiveData()
    }

    private fun updateRecipe(ingredient: Ingredient) {
        viewModelScope.launch {
            itemDao.transactionUpdateRecipe(ingredient)
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

    fun updateRecipe(
        recipeId: Int,
        recipeName: String,
        recipeCount: String
    ) {
        val updatedRecipe = getUpdatedRecipeEntry(recipeId, recipeName, recipeCount)
        updateRecipe(updatedRecipe)
    }
}

class IngredientAddViewModelFactory(private val itemDao: IngredientAddDao, private val ingredientDetailDao: IngredientDetailDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientAddViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IngredientAddViewModel(itemDao, ingredientDetailDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}