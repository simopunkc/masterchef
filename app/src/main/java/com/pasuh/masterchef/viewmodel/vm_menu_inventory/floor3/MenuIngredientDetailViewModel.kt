package com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor3

import androidx.lifecycle.*
import com.pasuh.masterchef.model.dao.dao_menu_inventory.floor3.MenuIngredientDetailDao
import com.pasuh.masterchef.model.erd.MenuIngredient
import kotlinx.coroutines.launch

class MenuIngredientDetailViewModel(private val itemDaoMenu: MenuIngredientDetailDao) : ViewModel() {

    fun deleteIngredient(menuIngredient: MenuIngredient) {
        viewModelScope.launch {
            itemDaoMenu.deleteIngredient(menuIngredient)
        }
    }

    fun retrieveIngredient(id: Int): LiveData<MenuIngredient> {
        return itemDaoMenu.getIngredient(id).asLiveData()
    }
}

class MenuIngredientDetailViewModelFactory(private val itemDaoMenu: MenuIngredientDetailDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuIngredientDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MenuIngredientDetailViewModel(itemDaoMenu) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}