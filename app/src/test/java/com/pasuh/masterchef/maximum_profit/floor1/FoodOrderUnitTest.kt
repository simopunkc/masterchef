package com.pasuh.masterchef.maximum_profit.floor1

import com.pasuh.masterchef.model.worker.FoodOrder
import org.junit.Assert.assertEquals
import org.junit.Test

class FoodOrderUnitTest : DependencyInjection() {

    @Test
    fun orderMenu_isCorrect() {
        // arrange
        val input1 = FoodOrder()
        input1.setIdIngredient(createFoodIdIngredient())
        input1.setIngredient(createFoodIngredient())

        val input2 = FoodOrder()
        input2.setIdIngredient(createFoodIdIngredient())
        input2.setIngredient(createFoodIngredient())

        val input3 = FoodOrder()
        input3.setIdIngredient(createFoodIdIngredient())
        input3.setIngredient(createFoodIngredient())

        val input4 = FoodOrder()
        input4.setIdIngredient(createFoodIdIngredient())
        input4.setIngredient(createFoodIngredient())

        val combination1 = listOf(22.0,18.0,15.0)
        val combination2 = listOf(22.0,15.0)
        val combination3 = listOf(18.0,15.0)
        val combination4 = listOf(22.0,18.0)

        // action
        input1.orderMenu(combination1)
        input2.orderMenu(combination2)
        input3.orderMenu(combination3)
        input4.orderMenu(combination4)

        // assert
        assertEquals(22.0, input1.currentProfit, .0)
        assertEquals(22.0, input2.currentProfit, .0)
        assertEquals(33.0, input3.currentProfit, .0)
        assertEquals(22.0, input4.currentProfit, .0)
    }

    @Test
    fun orderMenuAll_isCorrect() {
        // arrange
        val input1 = FoodOrder()
        input1.setIdIngredient(createFoodIdIngredient())
        input1.setIngredient(createFoodIngredient())

        val input2 = FoodOrder()
        input2.setIdIngredient(createFoodIdIngredient())
        input2.setIngredient(createFoodIngredient())

        val input3 = FoodOrder()
        input3.setIdIngredient(createFoodIdIngredient())
        input3.setIngredient(createFoodIngredient())

        val input4 = FoodOrder()
        input4.setIdIngredient(createFoodIdIngredient())
        input4.setIngredient(createFoodIngredient())

        val combination1 = listOf(22.0,18.0,15.0)
        val combination2 = listOf(22.0,15.0)
        val combination3 = listOf(18.0,15.0)
        val combination4 = listOf(22.0,18.0)

        // action
        val result1 = input1.orderMenuAll(combination1)
        val result2 = input2.orderMenuAll(combination2)
        val result3 = input3.orderMenuAll(combination3)
        val result4 = input4.orderMenuAll(combination4)

        // assert
        assertEquals(listOf(22.0).toString(), result1.menu)
        assertEquals(listOf(22.0).toString(), result2.menu)
        assertEquals(listOf(18.0,15.0).toString(), result3.menu)
        assertEquals(listOf(22.0).toString(), result4.menu)
    }
}