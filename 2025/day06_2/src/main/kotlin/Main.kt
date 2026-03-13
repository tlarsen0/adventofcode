package org.tlarsen.adventofcode2025.day06.part2

import java.io.File

fun main(argv: Array<String>) {
    println("AOC 2025, Day 6, Part 2 starting!!!!")
    println("Details of problem are here: https://adventofcode.com/2025/day/6#part2")

    val allFormulas = mutableListOf<CephalopodFormula>()
    val allCharacters = HashMap<Coord, Char>()
    var x = 0
    var y = 0

    File(argv[0]).forEachLine {
        // at the start of a line, reset x to 0
        x = 0
        for(theChar in it.toCharArray()) {
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
    val formulaSpans = mutableListOf<Pair<Int, Int>>()
    // the "bottom" of the data is the last y update
    val maxY = y -1
    println("Scanning row $maxY to determine blocks to scan:")



    /*
    var theTotal = 0L
    for(formula in allFormulas) {
        val answer = formula.doHomework()
        println("Formula: $formula".padEnd(30, ' ') + " = $answer")
        theTotal += answer
    }
    println("The total calculated using Cephalopod Math: $theTotal")
     */

    println("AOC 2025, Day 6, Part 2 completed!!!")
}
