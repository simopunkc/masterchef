package com.pasuh.masterchef.model.worker

import android.util.Log
import com.pasuh.masterchef.model.orm.MaxProfit
import com.pasuh.masterchef.model.orm.MenuIngredient
import com.pasuh.masterchef.model.orm.listIdRecipe
import com.pasuh.masterchef.model.orm.listRecipeQuantity

class FoodOrder {
    var currentMenus: MutableList<MenuIngredient> = mutableListOf()
    var currentProfit: Double = 0.0
    private var foodIdIngredient = HashMap<Int, Int>()
    private var foodIngredient = HashMap<String, MenuIngredient>()

    fun printStatusIngredient(){
        Log.e("remaining ingredients", this.foodIdIngredient.toString())
    }

    fun setIdIngredient(idIngredient: HashMap<Int, Int>){
        idIngredient.keys.map { key ->
            this.foodIdIngredient[key] = idIngredient.getValue(key)
        }
    }

    fun setIngredient(ingredient: HashMap<String, MenuIngredient>){
        ingredient.keys.map { key ->
            this.foodIngredient[key] = ingredient.getValue(key)
        }
    }

    fun clearResource() {
        this.foodIdIngredient.clear()
        this.foodIngredient.clear()
    }

    private fun cook(price: String) : Boolean {
        var status = false
        for(i in 0 until this.foodIngredient.getValue(price).listRecipeQuantity().count()){
            val idRecipe = this.foodIngredient.getValue(price).listIdRecipe()[i]
            if(this.foodIngredient.getValue(price).listRecipeQuantity()[i] <= this.foodIdIngredient.getValue(idRecipe)){
                this.foodIdIngredient[idRecipe] = this.foodIdIngredient.getValue(idRecipe) - this.foodIngredient.getValue(price).listRecipeQuantity()[i]
                status=true
            }else{
                status=false
                break
            }
        }
        return status
    }

    fun orderMenu(order: List<Double>) {
        for(i in 0 until order.count()){
            if(this.cook(order[i].toString())){
                this.currentProfit += order[i]
                foodIngredient[order[i].toString()]?.let { this.currentMenus.add(it) }
            }else{
                break
            }
        }
    }

    fun orderMenuAll(order: List<Double>) : MaxProfit {
        val cMenu: MutableList<Double> = mutableListOf()
        var cProfit = 0.0
        for(i in 0 until order.count()){
            if(this.cook(order[i].toString())){
                cProfit += order[i]
                cMenu.add(order[i])
            }else{
                break
            }
        }
        return MaxProfit(
            menu = cMenu.toString(),
            profit = cProfit
        )
    }
}