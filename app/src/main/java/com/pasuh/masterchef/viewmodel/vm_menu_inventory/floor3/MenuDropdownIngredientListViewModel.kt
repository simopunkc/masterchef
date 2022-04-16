package com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor3

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.pasuh.masterchef.model.dao.dao_menu_inventory.floor3.MenuDropdownIngredientListDao
import com.pasuh.masterchef.model.erd.Ingredient

class DropdownListViewModel(private val itemDaoMenuDropdownIngredient: MenuDropdownIngredientListDao) : ViewModel() {

    fun retrieveDropdownIngredient(id: Int): LiveData<List<Ingredient>> {
        return itemDaoMenuDropdownIngredient.getDropdownIngredient(id).asLiveData()
    }
}

class DropdownListViewModelFactory(private val itemDaoMenuDropdownIngredient: MenuDropdownIngredientListDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DropdownListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DropdownListViewModel(itemDaoMenuDropdownIngredient) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}