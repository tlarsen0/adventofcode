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

/**
 * Union-Find (Disjoint Set Union) data structure with path compression and
 * union-by-size. Tracks connected components in a graph.
 *
 * @property parent Array where each index stores its parent (root points to itself).
 * @property size Array storing the size of each tree rooted at the given index.
 * @param n Number of elements (initially each is its own component).
 */
class UnionFind(n: Int) {
    private val parent = IntArray(n) { it }
    private val size = IntArray(n) { 1 }

    /**
     * Finds the root (representative) of the set containing [a].
     * Applies path compression to flatten the tree.
     *
     * @param a Element to find.
     * @return Root element of the set.
     */
    fun find(a: Int): Int {
        var x = a
        while (parent[x] != x) {
            parent[x] = parent[parent[x]]  // path compression
            x = parent[x]
        }
        return x
    }

    /**
     * Unions the sets containing [a] and [b]. Uses the size of each set to
     * attach the smaller tree under the larger tree.
     *
     * @param a First element.
     * @param b Second element.
     */
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

    /**
     * Returns the sizes of all connected components, sorted descending.
     *
     * @param n Total number of elements to consider.
     * @return List of component sizes, largest first.
     */
    fun circuitSizes(n: Int): List<Int> {
        val counts = mutableMapOf<Int, Int>()
        for (i in 0 until n) {
            val root = find(i)
            counts[root] = (counts[root] ?: 0) + 1
        }
        return counts.values.sortedDescending()
    }
}


