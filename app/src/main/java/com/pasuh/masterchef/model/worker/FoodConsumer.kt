package com.pasuh.masterchef.model.worker

import android.util.Log
import com.pasuh.masterchef.model.orm.MaxProfit
import com.pasuh.masterchef.model.orm.MenuIngredient

class FoodConsumer {
    fun getMaximumProfit(combination: MutableList<List<Double>>, depInject: MutableList<FoodOrder>, newIdIngredient: HashMap<Int, Int>, newIngredient: HashMap<String, MenuIngredient>) : MaxProfit {
        var listIngredient: MutableList<MenuIngredient> = mutableListOf()
        var maxProfit = 0.0
        combination.forEachIndexed { i, arrOrder ->
            depInject[i].setIdIngredient(newIdIngredient)
            depInject[i].setIngredient(newIngredient)
            while (true) {
                depInject[i].orderMenu(arrOrder)
                if (depInject[i].currentProfit > maxProfit) {
                    listIngredient = depInject[i].currentMenus
                    maxProfit = depInject[i].currentProfit
                } else {
                    break
                }
            }
            depInject[i].clearResource()
        }
        return MaxProfit(
            menu = listIngredient.map { it.menuName } .toString(),
            profit = maxProfit
        )
    }

    fun getVariationProfit(combination: MutableList<List<Double>>, depInject: MutableList<FoodOrder>, newIdIngredient: HashMap<Int, Int>, newIngredient: HashMap<String, MenuIngredient>){
        combination.forEachIndexed { i, arrOrder ->
            var remainingBalance = 200.0
            val beginningBalance = 200.0
            val allMenu: MutableList<String> = mutableListOf()
            depInject[i].setIdIngredient(newIdIngredient)
            depInject[i].setIngredient(newIngredient)
            while(true){
                val result: MaxProfit = depInject[i].orderMenuAll(arrOrder)
                if(result.profit in 0.0..remainingBalance){
                    remainingBalance -= result.profit
                    allMenu.add(result.menu)
                }else{
                    break
                }
            }
            Log.e("iteration",(i+1).toString())
            Log.e("variation",arrOrder.toString())
            Log.e("profit achieved",(beginningBalance-remainingBalance).toString())
            Log.e("profit not achieved",remainingBalance.toString())
            depInject[i].printStatusIngredient()
            Log.e("total cooked menu",allMenu.count().toString())
            Log.e("list cooked menu",allMenu.toString())
            depInject[i].clearResource()
        }
    }
}