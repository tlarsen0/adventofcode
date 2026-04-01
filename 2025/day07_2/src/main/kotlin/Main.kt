package org.tlarsen.adventofcode2025.day07.part2

import java.io.File


fun main(args: Array<String>) {
    println("AOC 2025, Day 7, Part 2 starting!!!!")
    println("Details of problem are here: https://adventofcode.com/2025/day/7#part2")

    if (args.size != 1) {
        println("Please provide an input file. Exiting!")
        return
    }

    val tachyonManifold = TachyonManifold()

    File(args[0]).forEachLine {
        tachyonManifold.add(it)
    }

    println("Before processing...")
    println(tachyonManifold)

    println("Firing tachyons!")
    val timelineCount = tachyonManifold.fireTachyons()
    println("Beam split count: $timelineCount")
    //println("L27: tachyonManifold = $tachyonManifold")
    //println("L28: completed beams: ${tachyonManifold.completedBeams}")

    println("AOC 2025, Day 7, Part 2 completed!!!")
}