package org.tlarsen.adventofcode2025.day06.part2

class CephalopodFormula {
    private val params = mutableListOf<Int>()
    fun addParam(newParam: Int) {
        params.add(newParam)
    }

    var operation : CephalopodOperation = CephalopodOperation.NONE

    override fun toString(): String {
        return params.joinToString(operation.toString())
    }

    fun doHomework() : Long = params.map { it.toLong() }.reduce { acc, i -> operation.calculate(acc, i) }
}