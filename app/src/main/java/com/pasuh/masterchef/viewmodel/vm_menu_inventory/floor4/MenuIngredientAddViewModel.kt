package com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor4

import androidx.lifecycle.*
import com.pasuh.masterchef.model.dao.dao_menu_inventory.floor4.MenuIngredientAddDao
import com.pasuh.masterchef.model.erd.MenuIngredient
import kotlinx.coroutines.launch

class MenuIngredientAddViewModel(private val itemDaoMenu: MenuIngredientAddDao) : ViewModel() {

    fun isIngredientValid(ingredientQuantity: String): Boolean {
        if (ingredientQuantity.isBlank()) {
            return false
        }
        return true
    }

    fun addNewIngredient(
        idMenu: Int,
        idRecipe: Int,
        quantityRecipe: Int,
        recipeUpdated: String
    ) {
        val newIngredient = getNewIngredientEntry(idMenu, idRecipe, quantityRecipe, recipeUpdated)
        insertIngredient(newIngredient)
    }

    fun retrieveIngredient(id: Int): LiveData<MenuIngredient> {
        return itemDaoMenu.getIngredient(id).asLiveData()
    }

    fun updateIngredient(
        ingredientId: Int,
        idMenu: Int,
        idRecipe: Int,
        quantityRecipe: Int,
        recipeUpdated: String
    ) {
        val updatedIngredient = getUpdatedIngredientEntry(ingredientId, idMenu, idRecipe, quantityRecipe, recipeUpdated)
        updateIngredient(updatedIngredient)
    }

    private fun updateIngredient(menuIngredient: MenuIngredient) {
        viewModelScope.launch {
            itemDaoMenu.updateIngredient(menuIngredient)
        }
    }

    private fun insertIngredient(menuIngredient: MenuIngredient) {
        viewModelScope.launch {
            itemDaoMenu.insertIngredient(menuIngredient)
        }
    }

    private fun getUpdatedIngredientEntry(
        ingredientId: Int,
        idMenu: Int,
        idRecipe: Int,
        quantityRecipe: Int,
        recipeUpdated: String
    ): MenuIngredient {
        return MenuIngredient(
            id = ingredientId,
            idMenu = idMenu,
            idRecipe = idRecipe,
            recipeQuantity = quantityRecipe,
            recipeUpdated = recipeUpdated
        )
    }

    private fun getNewIngredientEntry(
        idMenu: Int,
        idRecipe: Int,
        quantityRecipe: Int,
        recipeUpdated: String
    ): MenuIngredient {
        return MenuIngredient(
            idMenu = idMenu,
            idRecipe = idRecipe,
            recipeQuantity = quantityRecipe,
            recipeUpdated = recipeUpdated
        )
    }
}

class MenuIngredientAddViewModelFactory(private val itemDaoMenu: MenuIngredientAddDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuIngredientAddViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MenuIngredientAddViewModel(itemDaoMenu) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}