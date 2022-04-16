package com.pasuh.masterchef.viewmodel.vm_ingredient_inventory.floor1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.pasuh.masterchef.model.dao.dao_ingredient_inventory.floor1.IngredientListDao
import com.pasuh.masterchef.model.erd.Ingredient

class IngredientListViewModel(itemDao: IngredientListDao) : ViewModel() {
    val allRecipes: LiveData<List<Ingredient>> = itemDao.getRecipes().asLiveData()
}

class IngredientListViewModelFactory(private val itemDao: IngredientListDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IngredientListViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}