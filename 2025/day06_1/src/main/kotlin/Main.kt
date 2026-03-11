package org.tlarsen.adventofcode2025

import java.io.File

fun main(argv: Array<String>) {
    println("AOC 2025, Day 6, Part 1 starting!!!!")
    println("Details of problem are here: https://adventofcode.com/2025/day/6#part1")

    val allFormulas = mutableListOf<CephalopodFormula>()
    var firstLine = true

    File(argv[0]).forEachLine {
        val theSplit = it.split(regex = "\\s+".toRegex()).filter { its -> its.isNotBlank() }
        var splitCounter = 0
        for(split in theSplit) {
            if(split.isNotEmpty()) {
                if (firstLine) {
                    // special parsing for the first line: use it to initialize array list.
                    val newFormula = CephalopodFormula()
                    newFormula.addParam(split.toInt())
                    allFormulas.add(newFormula)
                } else if((split == "+") || (split == "*")) {
                    // special parsing operator
                    val formula = allFormulas[splitCounter]
                    when (split) {
                        "+" -> {
                            formula.operation = CephalopodOperation.ADDITION
                        }
                        "*" -> {
                            formula.operation = CephalopodOperation.MULTIPLICATION
                        }
                        else -> {
                            println("WARN: Could not parse operator: $split")
                        }
                    }
                } else {
                    // normal number parsing
                    val formula = allFormulas[splitCounter]
                    formula.addParam(split.toInt())
                }
                splitCounter++
            }
        }
        firstLine = false
    }
    var theTotal = 0L
    for(formula in allFormulas) {
        val answer = formula.doHomeworkNew()
        println("Formula: $formula".padEnd(30, ' ') + " = $answer")
        theTotal += answer
    }

    println("The total calculated using Cephalopod Math: $theTotal")

    println("AOC 2025, Day 6, Part 1 completed!!!")
}
