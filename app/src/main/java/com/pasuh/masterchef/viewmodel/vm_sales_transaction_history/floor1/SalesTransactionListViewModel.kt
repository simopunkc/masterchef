package com.pasuh.masterchef.viewmodel.vm_sales_transaction_history.floor1

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.pasuh.masterchef.model.dao.dao_sales_transaction_history.floor1.SalesTransactionListDao
import com.pasuh.masterchef.model.orm.SalesTransactionHistory

class SalesTransactionListViewModel(itemDao: SalesTransactionListDao) : ViewModel() {
    val allTransactions: LiveData<List<SalesTransactionHistory>> = itemDao.getTransaksis().asLiveData()
}

class SalesTransactionListViewModelFactory(private val itemDao: SalesTransactionListDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SalesTransactionListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SalesTransactionListViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}