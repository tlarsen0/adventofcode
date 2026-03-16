package org.tlarsen.adventofcode2025.day06.part2

import java.io.File

fun main(argv: Array<String>) {
    println("AOC 2025, Day 6, Part 2 starting!!!!")
    println("Details of problem are here: https://adventofcode.com/2025/day/6#part2")

    val allFormulas = mutableListOf<CephalopodFormula>()
    val allCharacters = HashMap<Coord, Char>()
    var x = 0
    var maxX = 0
    var y = 0

    File(argv[0]).forEachLine {
        maxX = maxOf(maxX, it.length)
        // at the start of a line, reset x to 0
        x = 0
        for (theChar in it.toCharArray()) {
            val newCoord = Coord(x, y)
            allCharacters[newCoord] = theChar
            x++
        }
        // line complete, increment y
        y++
    }

    val formulaCount = allCharacters.filter { it.value == '*' || it.value == '+' }.count()
    println("Number of formulas: $formulaCount")
    // calculate spans for each formula.
    val formulaSpans = mutableListOf<Int>()
    // the "bottom" of the data is the last y update
    val maxY = y - 1
    println("Scanning row $maxY, columns 0 to $maxX to determine blocks to scan:")
    var spanStart = 0
    for (scanX in 1..maxX) {
        val coord = Coord(scanX, maxY)
        // grab character at scanX, maxY, otherwise break out of loop (trying to read beyond the end of file)
        val theChar = allCharacters[coord] ?: break
        if ((theChar == ' ') || (spanStart == scanX)) {
            continue
        } else {
            formulaSpans.add(scanX)
            spanStart = scanX
        }
    }

    // formulaSpans should now contain indexes where blocks end. example: [0, 3, 8] means 0 to 2, 3 to 7, 8 to maxX/end of line
    formulaSpans.add(maxX + 1) // add end of line
    spanStart = 0
    for (spanEnd in formulaSpans) {
        val cephalopodParser = CephalopodParser(spanStart, spanEnd, allCharacters, maxY)
        allFormulas.add(cephalopodParser.findFormulaInSpan())
        spanStart = spanEnd
    }

    var theTotal = 0L
    for (formula in allFormulas) {
        val answer = formula.doHomework()
        println("Formula: $formula".padEnd(30, ' ') + " = $answer")
        theTotal += answer
    }
    // Correct answer: 10188206723429
    println("The total calculated using Cephalopod Math: $theTotal")

    println("AOC 2025, Day 6, Part 2 completed!!!")
}
