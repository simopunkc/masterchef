package com.pasuh.masterchef.model.worker

class Combination {
    private var singleResult: MutableList<Int> = mutableListOf()
    private var end = 0
    private var length = 0

    private var listCombination: MutableList<String> = mutableListOf()

    private fun calculateCombination(start: Int, index: Int) {
        if (index == this.end) {
            this.listCombination.add(gson.toJson(this.singleResult))
            return
        }
        for (i in start until length) {
            if(index < this.singleResult.count()){
                this.singleResult[index] = i
            }else{
                this.singleResult.add(i)
            }
            calculateCombination(i + 1, index + 1)
        }
    }

    fun startCombination(input: List<Int>) : MutableList<String> {
        this.end = input[1]
        this.length = input[0]
        this.calculateCombination(0, 0)
        return listCombination
    }
}