package org.tlarsen.adventofcode2025.day09.part1

import java.io.File
import kotlin.math.abs

fun main(args: Array<String>) {
    println("AOC 2025, Day 9, Part 1 starting!!!!")
    println("Details of problem are here: https://adventofcode.com/2025/day/9#part1")

    val allCoords = ArrayList<Coord>()
    File(args[0]).forEachLine {
        val theSplit = it.split(',')
        allCoords.add(Coord(theSplit[0].toLong(), theSplit[1].toLong()))
    }

    val allArea = HashMap<Pair<Coord, Coord>, Long>()

    for((i, coordI) in allCoords.withIndex()) {
        for(j in i + 1 until allCoords.size) {
            val coordJ = allCoords[j]
            allArea[Pair(coordI, coordJ)] = ((abs(coordI.x - coordJ.x) + 1) * (abs(coordI.y - coordJ.y) + 1))
        }
    }

    val maxEntry = allArea.entries.maxBy { it.value }
    println("Max Area, from (${maxEntry.key.first.x} , ${maxEntry.key.first.y}) to (${maxEntry.key.second.x} , ${maxEntry.key.second.y}): ${maxEntry.value}")

    println("AOC 2025, Day 9, Part 1 completed!!!")
}

/**
 * My custom Coord class that holds X, Y coordinates. Using this instead of Pair for clean context.
 */
data class Coord(val x: Long, val y: Long)