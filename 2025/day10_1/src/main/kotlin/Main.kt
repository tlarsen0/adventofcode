package org.tlarsen.adventofcode2025.day10.part1

import java.io.File

fun main(args: Array<String>) {
    println("AOC 2025, Day 10, Part 1 starting!!!!")
    println("Details of problem are here: https://adventofcode.com/2025/day/10")

    //val allIndicatorLights = mutableListOf<IndicatorLights>()
    val allMachines = mutableListOf<Machine>()
    File(args[0]).forEachLine { theLine ->
        val indicatorSplit = Regex("\\[([^]]*)]").find(theLine)?.groupValues?.getOrNull(1)
        val wiringSplit = Regex("\\(([^)]*)\\)").findAll(theLine).map { it.groupValues[1] }.toList()
        val wiringNodes = wiringSplit.map { ws -> ws.split(',').map { it.toInt() } }

        indicatorSplit?.let {
            allMachines.add(Machine(IndicatorLights(it), Wiring(wiringNodes)))
        }
    }

    var totalPresses = 0L
    for((indicatorLights, wiringNodes) in allMachines) {
        val buttonMasks = wiringNodes.buttons.map { b -> b.fold(0) { acc, i -> acc or (1 shl i) } }
        totalPresses += minPresses(indicatorLights.getLights(), buttonMasks)
    }

    println("Total presses, minimum: $totalPresses")
    println("AOC 2025, Day 10, Part 1 completed!!!")
}

data class Machine(val indicatorLights: IndicatorLights, val wiringNodes: Wiring)

/**
 * The target indicator lights for a machine, encoded as a string where '#'
 * marks a light that must be on and '.' a light that must be off.
 *
 * [getLights] converts that string into a bitmask where bit i is set when the
 * light at position i is '#'. The mask is used as the target that a subset of
 * button presses must XOR to.
 */
data class IndicatorLights(val lights: String) {
    fun getLights(): Int {
        return lights.foldIndexed(0) { i, acc, c ->
            if (c == '#') acc or (1 shl i) else acc
        }
    }
}

data class Wiring(val buttons: List<List<Int>>)


/**
 * Returns the minimum number of button presses needed to turn on exactly the
 * target indicator lights.
 *
 * Each button press toggles (XORs) every light it is wired to, so the task
 * reduces to finding the smallest subset of button masks whose XOR equals the
 * target mask. A brute-force check of all 2^n subsets is too slow, so this uses
 * meet-in-the-middle: it enumerates all XOR patterns of the right half
 * (mapping each pattern to its minimum press count), then for each left-half
 * pattern looks up the complement needed to reach the target and keeps the best
 * total. Runs in O(2^(n/2)) time.
 */
fun minPresses(target: Int, buttons: List<Int>): Int {
    val m = buttons.size
    val mid = m / 2
    val left = buttons.subList(0, mid)
    val right = buttons.subList(mid, m)

    // Run through right half: pattern -> min press count
    val rightMap = mutableMapOf<Int, Int>()
    for (mask in 0 until (1 shl right.size)) {
        var pattern = 0
        var presses = 0
        for (i in right.indices) {
            if (mask and (1 shl i) != 0) {
                pattern = pattern xor right[i]
                presses++
            }
        }
        val prev = rightMap[pattern]
        if (prev == null || presses < prev) rightMap[pattern] = presses
    }

    // Run through left half, look up complement in right map
    var best = Int.MAX_VALUE
    for (mask in 0 until (1 shl left.size)) {
        var pattern = 0
        var presses = 0
        for (i in left.indices) {
            if (mask and (1 shl i) != 0) {
                pattern = pattern xor left[i]
                presses++
            }
        }
        val need = target xor pattern
        val rightPresses = rightMap[need] ?: continue
        val total = presses + rightPresses
        if (total < best) best = total
    }

    return best
}