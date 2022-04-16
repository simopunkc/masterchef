package com.pasuh.masterchef.viewmodel.vm_restock_history.floor2

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.pasuh.masterchef.model.dao.dao_restock_history.floor2.RestockDetailDao
import com.pasuh.masterchef.model.orm.RestockHistory

class RestockDetailViewModel(private val itemDao: RestockDetailDao) : ViewModel() {
    fun retrieveRestock(id: Int): LiveData<RestockHistory> {
        return itemDao.getRestock(id).asLiveData()
    }

}

class RestockDetailViewModelFactory(private val itemDao: RestockDetailDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestockDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestockDetailViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}