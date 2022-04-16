package com.pasuh.masterchef.viewmodel.vm_maximum_profit.floor1

import android.app.Application
import androidx.lifecycle.*
import androidx.work.*
import com.pasuh.masterchef.model.dao.dao_maximum_profit.floor1.ProfitListDao
import com.pasuh.masterchef.model.orm.MaxProfit
import com.pasuh.masterchef.model.orm.MenuIngredient
import com.pasuh.masterchef.model.orm.TotalCount
import com.pasuh.masterchef.model.util.COMBINATION_CALCULATION_NAME
import com.pasuh.masterchef.model.util.TAG_OUTPUT
import com.pasuh.masterchef.model.worker.CleanupWorker
import com.pasuh.masterchef.model.worker.ProfitWorker

class ProfitListViewModel(private val profitListDao: ProfitListDao, application: Application) : ViewModel() {
    private val workManager = WorkManager.getInstance(application)
    internal var outputWorkInfos: LiveData<List<WorkInfo>> = workManager.getWorkInfosByTagLiveData(
        TAG_OUTPUT
    )

    private var _allProfits: MutableLiveData<MutableList<MaxProfit>> = MutableLiveData<MutableList<MaxProfit>>()
    val allProfits get() = _allProfits

    fun isNumberValid(num: Int): Boolean {
        if (num > 0) {
            return true
        }
        return false
    }

    fun getTotalItem() : LiveData<TotalCount> {
        return profitListDao.getTotalMenu().asLiveData()
    }

    fun getListIngredient() : LiveData<List<MenuIngredient>> {
        return profitListDao.getListIngredients().asLiveData()
    }

    internal fun cancelWork() {
        workManager.cancelUniqueWork(COMBINATION_CALCULATION_NAME)
    }

    internal fun applyBlur(nObject: Int, rObject: Int, gsonObject: String) {
        var continuation = workManager
            .beginUniqueWork(
                COMBINATION_CALCULATION_NAME,
                ExistingWorkPolicy.REPLACE,
                OneTimeWorkRequest.from(CleanupWorker::class.java)
            )

        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .build()

        for (i in rObject until (nObject+1)) {
            val jobBuilder = OneTimeWorkRequestBuilder<ProfitWorker>()
                .setConstraints(constraints)
                .addTag(TAG_OUTPUT)

            val setCombination = "${nObject},${i}"
            val input = Data.Builder()
                .putString("key", setCombination)
                .putString("obj", gsonObject)
                .build()

            jobBuilder.setInputData(input)

            continuation = continuation.then(jobBuilder.build())
        }

        continuation.enqueue()
    }

    fun clearAllProfits(){
        _allProfits.value = mutableListOf()
    }

    fun addAllProfits(profit: MaxProfit){
        val list = mutableListOf<MaxProfit>()
        _allProfits.value?.let { list.addAll(it) }
        list.add(profit)
        _allProfits.value = list.distinctBy { it.profit } as MutableList
    }
}

class ProfitListViewModelFactory(private val profitListDao: ProfitListDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfitListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfitListViewModel(profitListDao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}