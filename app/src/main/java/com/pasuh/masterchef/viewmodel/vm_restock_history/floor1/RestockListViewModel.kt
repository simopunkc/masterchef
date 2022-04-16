package com.pasuh.masterchef.viewmodel.vm_restock_history.floor1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.pasuh.masterchef.model.dao.dao_restock_history.floor1.RestockListDao
import com.pasuh.masterchef.model.orm.RestockHistory

class RestockListViewModel(itemDao: RestockListDao) : ViewModel() {
    val allRestocks: LiveData<List<RestockHistory>> = itemDao.getRestocks().asLiveData()
}

class RestockListViewModelFactory(private val itemDao: RestockListDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestockListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestockListViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}