package org.tlarsen.adventofcode2025.day06.part2

class CephalopodParser (val spanStart: Int, val spanEnd: Int, val allCharacters: MutableMap<Coord, Char>, val maxY : Int) {

    fun findFormulaInSpan() : CephalopodFormula {
        val newFormula = CephalopodFormula()
        for(x in spanStart ..< (spanEnd - 1)) {
            newFormula.addParam(getCephalopodNumberAt(x, maxY))
            if(newFormula.operation == CephalopodOperation.NONE) {
                newFormula.operation = getCephalopodOperation(x, maxY)
            }
        }
        return newFormula
    }

    private fun getCephalopodNumberAt(x: Int, maxY: Int) : Int {
        var result = 0

        for(y in 0..maxY) {
            val theChar = allCharacters[Coord(x, y)] ?: continue
            if(theChar.isDigit()) {
                result = result * 10 + Integer.parseInt(theChar.toString())
            }
        }
        return result
    }

    private fun getCephalopodOperation(x: Int, maxY: Int) : CephalopodOperation {
        for(y in 0..maxY) {
            val theChar = allCharacters[Coord(x, y)] ?: CephalopodOperation.NONE
            if(theChar == '+')
                return CephalopodOperation.ADDITION
            else if(theChar == '*')
                return CephalopodOperation.MULTIPLICATION
        }
        return CephalopodOperation.NONE
    }
}
