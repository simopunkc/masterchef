package com.pasuh.masterchef.viewmodel.vm_sales_transaction_history.floor2

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.pasuh.masterchef.model.dao.dao_sales_transaction_history.floor2.SalesTransactionDetailDao
import com.pasuh.masterchef.model.orm.SalesTransactionHistory

class SalesTransactionDetailViewModel(private val itemDao: SalesTransactionDetailDao) : ViewModel() {
    fun retrieveTransaksi(id: Int): LiveData<SalesTransactionHistory> {
        return itemDao.getTransaksi(id).asLiveData()
    }
}

class SalesTransactionDetailViewModelFactory(private val itemDao: SalesTransactionDetailDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SalesTransactionDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SalesTransactionDetailViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}