package com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor2

import androidx.lifecycle.*
import com.pasuh.masterchef.model.dao.dao_menu_inventory.floor2.MenuAddDao
import com.pasuh.masterchef.model.erd.MenuFood
import kotlinx.coroutines.launch

class MenuAddViewModel(private val itemDao: MenuAddDao) : ViewModel() {
    private fun insertItem(menuFood: MenuFood) {
        viewModelScope.launch {
            itemDao.insertMenu(menuFood)
        }
    }

    private fun getNewItemEntry(itemName: String, itemPrice: String): MenuFood {
        return MenuFood(
            itemName = itemName,
            itemPrice = itemPrice.toDouble()
        )
    }

    fun addNewItem(itemName: String, itemPrice: String) {
        val newItem = getNewItemEntry(itemName, itemPrice)
        insertItem(newItem)
    }

    fun isMenuValid(itemName: String, itemPrice: String): Boolean {
        if (itemName.isBlank() || itemPrice.isBlank()) {
            return false
        }
        return true
    }

    fun retrieveItem(id: Int): LiveData<MenuFood> {
        return itemDao.getItem(id).asLiveData()
    }

    private fun updateItem(menuFood: MenuFood) {
        viewModelScope.launch {
            itemDao.updateMenu(menuFood)
        }
    }

    private fun getUpdatedItemEntry(
        itemId: Int,
        itemName: String,
        itemPrice: String
    ): MenuFood {
        return MenuFood(
            id = itemId,
            itemName = itemName,
            itemPrice = itemPrice.toDouble()
        )
    }

    fun updateItem(
        itemId: Int,
        itemName: String,
        itemPrice: String
    ) {
        val updatedItem = getUpdatedItemEntry(itemId, itemName, itemPrice)
        updateItem(updatedItem)
    }
}

class MenuAddViewModelFactory(private val itemDao: MenuAddDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuAddViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MenuAddViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}