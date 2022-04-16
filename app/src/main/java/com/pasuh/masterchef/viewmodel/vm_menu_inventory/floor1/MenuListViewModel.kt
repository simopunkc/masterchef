package com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.pasuh.masterchef.model.dao.dao_menu_inventory.floor1.MenuListDao
import com.pasuh.masterchef.model.erd.MenuFood

class MenuListViewModel(itemDao: MenuListDao) : ViewModel() {
    val allItems: LiveData<List<MenuFood>> = itemDao.getItems().asLiveData()
}

class MenuListViewModelFactory(private val itemDao: MenuListDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MenuListViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}