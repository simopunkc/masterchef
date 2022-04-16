package com.pasuh.masterchef.model.worker

import android.content.Context
import androidx.work.*
import com.google.gson.Gson
import com.pasuh.masterchef.model.erd.*
import com.pasuh.masterchef.model.orm.*
import com.pasuh.masterchef.model.orm.MenuIngredient
import com.pasuh.masterchef.model.util.TAG_OUTPUT

val gson = Gson()

class ProfitWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {
        Delay().sleepTemp()
        return try {
            val input = inputData.getString("key")?.split(",")?.map { it.toInt() }
            val obj = inputData.getString("obj")
            if(!input.isNullOrEmpty() && !obj.isNullOrEmpty()) {
                val newIdIngredient = HashMap<Int, Int>()
                val newIngredient = HashMap<String, MenuIngredient>()
                val keyCombination: MutableList<Double> = mutableListOf()
                gson.fromJson(obj, Array<MenuIngredient>::class.java).asList().forEach { item ->
                    val listId = item.listIdRecipe()
                    val listStock = item.listStockQuantity()
                    for(i in 0 until listId.count()){
                        if(!newIdIngredient.containsKey(listId[i])) {
                            newIdIngredient[listId[i]] = listStock[i]
                        }
                    }
                    keyCombination.add(item.menuPrice)
                    newIngredient[item.menuPrice.toString()] = item
                }
                val listCombination = Combination().startCombination(input)
                val combination: MutableList<List<Double>> = mutableListOf()
                val depObj: MutableList<FoodOrder> = mutableListOf()
                for(i in 0 until listCombination.count()){
                    combination.add(gson.fromJson(listCombination[i], Array<Int>::class.java).asList().map { keyCombination[it] })
                    depObj.add(FoodOrder())
                }
//                // for debugging
//                FoodConsumer().getVariationProfit(combination,depObj,newIdIngredient,newIngredient)

                val result: MaxProfit = FoodConsumer().getMaximumProfit(combination,depObj,newIdIngredient,newIngredient)
                val output = workDataOf(TAG_OUTPUT to gson.toJson(result))
                Result.success(output)
            }else{
                Result.success()
            }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            Result.failure()
        }
    }

    class Factory : WorkerFactory() {
        override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker {
            return ProfitWorker(appContext, workerParameters)
        }
    }
}