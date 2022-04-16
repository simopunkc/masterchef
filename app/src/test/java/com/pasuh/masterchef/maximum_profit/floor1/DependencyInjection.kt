package com.pasuh.masterchef.maximum_profit.floor1

import com.pasuh.masterchef.model.orm.MenuIngredient

open class DependencyInjection {
    fun createFoodIdIngredient() : HashMap<Int, Int> {
        val foodIdIngredient = HashMap<Int, Int>()
        foodIdIngredient[1]=3
        foodIdIngredient[2]=2
        foodIdIngredient[3]=7
        return foodIdIngredient
    }

    fun createFoodIngredient() : HashMap<String, MenuIngredient> {
        val foodIngredient = HashMap<String, MenuIngredient>()
        foodIngredient["22.0"]= MenuIngredient(1,"Grilled Lobster",22.0,"1,2,3","1,2,3","3,2,7")
        foodIngredient["18.0"]= MenuIngredient(2,"Roast Duck",18.0,"1,2,3","2,1,1","3,2,7")
        foodIngredient["15.0"]= MenuIngredient(3,"Grilled Chicken",15.0,"1,2,3","1,1,1","3,2,7")
        return foodIngredient
    }
}