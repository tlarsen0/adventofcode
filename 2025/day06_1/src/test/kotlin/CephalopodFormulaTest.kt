package org.tlarsen.adventofcode2025

import kotlin.test.Test
import kotlin.test.assertEquals

class CephalopodFormulaTest {

    @Test
    fun `addition of two numbers`() {
        val formula = CephalopodFormula()
        formula.addParam(5)
        formula.addParam(3)
        formula.operation = CephalopodOperation.ADDITION

        assertEquals(8L, formula.doHomework())
    }

    @Test
    fun `multiplication of two numbers`() {
        val formula = CephalopodFormula()
        formula.addParam(4)
        formula.addParam(2)
        formula.operation = CephalopodOperation.MULTIPLICATION

        assertEquals(8L, formula.doHomework())
    }

    @Test
    fun `multiple numbers addition`() {
        val formula = CephalopodFormula()
        formula.addParam(1)
        formula.addParam(2)
        formula.addParam(3)
        formula.operation = CephalopodOperation.ADDITION

        assertEquals(6L, formula.doHomework())
    }

    @Test
    fun `multiple numbers multiplication`() {
        val formula = CephalopodFormula()
        formula.addParam(2)
        formula.addParam(3)
        formula.addParam(4)
        formula.operation = CephalopodOperation.MULTIPLICATION

        assertEquals(24L, formula.doHomework())
    }
}