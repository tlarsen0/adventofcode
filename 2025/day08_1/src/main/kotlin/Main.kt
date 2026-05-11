package org.tlarsen.adventofcode2025.day08.part1

import java.io.File

fun main(args: Array<String>) {
    println("AOC 2025, Day 8, Part 1 starting!!!!")
    println("Details of problem are here: https://adventofcode.com/2025/day/8#part1")

    val allJunctions = ArrayList<JunctionCoords>()
    File(args[0]).forEachLine {
        val theSplit = it.split(",")
        allJunctions.add(JunctionCoords(theSplit[0].toLong(), theSplit[1].toLong(), theSplit[2].toLong()))
    }

    // The problem statement for Day 8, part 1 mentions they want 10 junctions for example data, 1000 for full data.
    println("Answer for ${args[0]}: ${solveForNJunctions(allJunctions, 1000)}")

    println("AOC 2025, Day 8, Part 1 completed!!!")
}

/**
 * Represents the Junction Box x, y, z coordinates
 */
data class JunctionCoords(val x: Long, val y: Long, val z: Long)

/**
 * Represents the wiring between Junction Boxes.
 */
data class JunctionEdge(val distance: Long, val x: Int, val y: Int)


fun solveForNJunctions(allJunctions: ArrayList<JunctionCoords>, solveForJunctions: Int): Long {
    val numJunctions = allJunctions.size

    val edges = ArrayList<JunctionEdge>()
    for(x in 0 until numJunctions) {
        for(y in x + 1 until numJunctions) {
            val dx = allJunctions[x].x - allJunctions[y].x
            val dy = allJunctions[x].y - allJunctions[y].y
            val dz = allJunctions[x].z - allJunctions[y].z
            edges.add(JunctionEdge((dx * dx + dy * dy + dz * dz), x, y))
        }
    }
    edges.sortBy { it.distance }

    val unionFind = UnionFind(numJunctions)
    for(edge in edges.take(solveForJunctions)) {
        unionFind.union(edge.x, edge.y)
    }

    val sizes = unionFind.circuitSizes(numJunctions)
    return sizes[0].toLong() * sizes[1].toLong() * sizes[2].toLong()
}

class UnionFind(n: Int) {
    private val parent = IntArray(n) { it }
    private val size = IntArray(n) { 1 }

    fun find(a: Int): Int {
        var x = a
        while (parent[x] != x) {
            parent[x] = parent[parent[x]]  // path compression
            x = parent[x]
        }
        return x
    }

    fun union(a: Int, b: Int) {
        val ra = find(a)
        val rb = find(b)
        if (ra == rb) return
        if (size[ra] >= size[rb]) {
            parent[rb] = ra
            size[ra] += size[rb]
        } else {
            parent[ra] = rb
            size[rb] += size[ra]
        }
    }

    fun circuitSizes(n: Int): List<Int> {
        val counts = mutableMapOf<Int, Int>()
        for (i in 0 until n) {
            val root = find(i)
            counts[root] = (counts[root] ?: 0) + 1
        }
        return counts.values.sortedDescending()
    }
}


