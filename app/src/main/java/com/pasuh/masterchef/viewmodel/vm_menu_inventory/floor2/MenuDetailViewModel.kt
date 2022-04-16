package com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor2

import androidx.lifecycle.*
import com.pasuh.masterchef.model.dao.dao_menu_inventory.floor2.MenuDetailDao
import com.pasuh.masterchef.model.erd.MenuFood
import com.pasuh.masterchef.model.orm.MenuIngredientSingle
import com.pasuh.masterchef.model.orm.TotalCount
import kotlinx.coroutines.launch

class MenuDetailViewModel(private val itemDao: MenuDetailDao) : ViewModel() {

    fun retrieveItem(id: Int): LiveData<MenuFood> {
        return itemDao.getItem(id).asLiveData()
    }

    fun deleteItem(menuFood: MenuFood) {
        viewModelScope.launch {
            itemDao.deleteMenu(menuFood)
        }
    }

    fun retrieveListIngredient(id: Int): LiveData<List<MenuIngredientSingle>> {
        return itemDao.getListIngredient(id).asLiveData()
    }

    fun getTotalRecipe(): LiveData<TotalCount> {
        return itemDao.getTotalRecipe().asLiveData()
    }
}

class MenuDetailViewModelFactory(private val itemDao: MenuDetailDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MenuDetailViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}