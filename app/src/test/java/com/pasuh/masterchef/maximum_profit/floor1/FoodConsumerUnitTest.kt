package com.pasuh.masterchef.maximum_profit.floor1

import com.pasuh.masterchef.model.orm.MaxProfit
import com.pasuh.masterchef.model.worker.FoodConsumer
import com.pasuh.masterchef.model.worker.FoodOrder
import org.junit.Assert.assertEquals
import org.junit.Test

class FoodConsumerUnitTest : DependencyInjection() {

    @Test
    fun getMaximumProfit_isCorrect() {
        // arrange
        val combination1: MutableList<List<Double>> = mutableListOf()
        combination1.add(listOf(22.0,15.0))
        combination1.add(listOf(22.0,18.0))
        combination1.add(listOf(18.0,15.0))

        val combination2: MutableList<List<Double>> = mutableListOf()
        combination2.add(listOf(22.0))
        combination2.add(listOf(18.0))
        combination2.add(listOf(15.0))

        val combination3: MutableList<List<Double>> = mutableListOf()
        combination3.add(listOf(22.0,18.0,15.0))

        val depObj1: MutableList<FoodOrder> = mutableListOf()
        depObj1.add(FoodOrder())
        depObj1.add(FoodOrder())
        depObj1.add(FoodOrder())

        val depObj2: MutableList<FoodOrder> = mutableListOf()
        depObj2.add(FoodOrder())
        depObj2.add(FoodOrder())
        depObj2.add(FoodOrder())

        val depObj3: MutableList<FoodOrder> = mutableListOf()
        depObj3.add(FoodOrder())

        // action
        val result1: MaxProfit = FoodConsumer().getMaximumProfit(combination1,depObj1,createFoodIdIngredient(),createFoodIngredient())
        val result2: MaxProfit = FoodConsumer().getMaximumProfit(combination2,depObj2,createFoodIdIngredient(),createFoodIngredient())
        val result3: MaxProfit = FoodConsumer().getMaximumProfit(combination3,depObj3,createFoodIdIngredient(),createFoodIngredient())

        // assert
        assertEquals(33.0, result1.profit, .0)
        assertEquals(22.0, result2.profit, .0)
        assertEquals(22.0, result3.profit, .0)
    }
}