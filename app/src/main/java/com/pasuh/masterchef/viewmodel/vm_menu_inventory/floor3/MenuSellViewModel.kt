package com.pasuh.masterchef.viewmodel.vm_menu_inventory.floor3

import androidx.lifecycle.*
import com.pasuh.masterchef.model.dao.dao_menu_inventory.floor2.MenuDetailDao
import com.pasuh.masterchef.model.dao.dao_menu_inventory.floor3.MenuSellDao
import com.pasuh.masterchef.model.erd.Ingredient
import com.pasuh.masterchef.model.erd.MenuFood
import com.pasuh.masterchef.model.erd.SalesTransaction
import com.pasuh.masterchef.model.orm.MenuIngredient
import kotlinx.coroutines.launch
import java.text.DateFormat.getDateInstance
import java.text.DateFormat.getTimeInstance
import java.util.*

class MenuSellViewModel(private val itemDao: MenuSellDao, private val itemDetailDao: MenuDetailDao) : ViewModel() {

    fun isSellValid(itemQuantity: String): Boolean {
        if (itemQuantity.isBlank()) {
            return false
        }
        return true
    }

    fun retrieveSingleIngredients(id: Int) : LiveData<MenuIngredient> {
        return itemDao.getSingleIngredients(id).asLiveData()
    }

    fun getListTransaksiRecipe(list: List<Int>, length: Int) : LiveData<List<Ingredient>> {
        return itemDao.getTransactionUpdatedRecipes(list, length).asLiveData()
    }

    fun retrieveItem(id: Int): LiveData<MenuFood> {
        return itemDetailDao.getItem(id).asLiveData()
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
            itemDao.transactionUpdateRecipe(ingredient)
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

    fun addNewTransaksi(menuFood: MenuFood, quantity: Int) {
        viewModelScope.launch {
            itemDao.insertTransaksi(
                SalesTransaction(
                    idMenu = menuFood.id,
                    quantityInTransaction = quantity,
                    tglInserted = getDateInstance().format(Date()),
                    jamInserted = getTimeInstance().format(Date())
                )
            )
        }
    }
}

class MenuSellViewModelFactory(private val itemDao: MenuSellDao, private val itemDetailDao: MenuDetailDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MenuSellViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MenuSellViewModel(itemDao,itemDetailDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}