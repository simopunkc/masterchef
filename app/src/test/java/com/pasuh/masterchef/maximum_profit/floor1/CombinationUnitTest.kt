package com.pasuh.masterchef.maximum_profit.floor1

import com.pasuh.masterchef.model.worker.Combination
import com.pasuh.masterchef.model.worker.gson
import org.junit.Assert.assertEquals
import org.junit.Test

class CombinationUnitTest {

    @Test
    fun generateList_isCorrect() {
        // arrange
        val input1 = listOf(3,1)
        val input2 = listOf(3,2)
        val input3 = listOf(3,3)
        val input4 = listOf(4,2)
        val input5 = listOf(4,7)

        // action
        val action1 = Combination().startCombination(input1)
        val action2 = Combination().startCombination(input2)
        val action3 = Combination().startCombination(input3)
        val action4 = Combination().startCombination(input4)
        val action5 = Combination().startCombination(input5)

        // assert
        val assert1: MutableList<String> = mutableListOf()
        assert1.add(gson.toJson(listOf(0)))
        assert1.add(gson.toJson(listOf(1)))
        assert1.add(gson.toJson(listOf(2)))
        assertEquals(assert1, action1)

        val assert2: MutableList<String> = mutableListOf()
        assert2.add(gson.toJson(listOf(0,1)))
        assert2.add(gson.toJson(listOf(0,2)))
        assert2.add(gson.toJson(listOf(1,2)))
        assertEquals(assert2, action2)

        val assert3: MutableList<String> = mutableListOf()
        assert3.add(gson.toJson(listOf(0,1,2)))
        assertEquals(assert3, action3)

        val assert4: MutableList<String> = mutableListOf()
        assert4.add(gson.toJson(listOf(0,1)))
        assert4.add(gson.toJson(listOf(0,2)))
        assert4.add(gson.toJson(listOf(0,3)))
        assert4.add(gson.toJson(listOf(1,2)))
        assert4.add(gson.toJson(listOf(1,3)))
        assert4.add(gson.toJson(listOf(2,3)))
        assertEquals(assert4, action4)

        val assert5: MutableList<String> = mutableListOf()
        assertEquals(assert5, action5)
    }
}