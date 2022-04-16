package com.pasuh.masterchef.viewmodel.vm_ingredient_inventory.floor2

import androidx.lifecycle.*
import com.pasuh.masterchef.model.dao.dao_ingredient_inventory.floor2.IngredientDetailDao
import com.pasuh.masterchef.model.erd.Ingredient
import kotlinx.coroutines.launch

class IngredientDetailViewModel(private val itemDao: IngredientDetailDao) : ViewModel() {

    fun deleteRecipe(ingredient: Ingredient) {
        viewModelScope.launch {
            itemDao.deleteRecipe(ingredient)
        }
    }

    fun retrieveRecipe(id: Int): LiveData<Ingredient> {
        return itemDao.getRecipe(id).asLiveData()
    }
}

class IngredientDetailViewModelFactory(private val itemDao: IngredientDetailDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return IngredientDetailViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}