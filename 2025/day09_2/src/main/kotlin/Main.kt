package org.tlarsen.adventofcode2025.day09.part2

import java.io.File

fun main(args: Array<String>) {
    println("AOC 2025, Day 9, Part 2 starting!!!!")
    println("Details of problem are here: https://adventofcode.com/2025/day/9#part2")

    val allCords = mutableListOf<Coord>()
    File(args[0]).forEachLine {
        val theSplit = it.split(',')
        allCords.add(Coord(theSplit[0].toLong(), theSplit[1].toLong()))
    }

    val interior = InteriorRanges(allCords)
    var maxArea = 0L

    for (i in allCords.indices) {
        for (j in i + 1 until allCords.size) {
            val a = allCords[i]
            val b = allCords[j]
            val minX = minOf(a.x, b.x)
            val maxX = maxOf(a.x, b.x)
            val minY = minOf(a.y, b.y)
            val maxY = maxOf(a.y, b.y)

            if (interior.rectInside(minX, maxX, minY, maxY)) {
                val area = (maxX - minX + 1L) * (maxY - minY + 1L)
                if (area > maxArea) maxArea = area
            }
        }
    }

    println("The max area found: $maxArea")
    println("AOC 2025, Day 9, Part 1 completed!!!")
}

/**
 * My custom Coord class that holds X, Y coordinates. Using this instead of Pair for clean context.
 */
data class Coord(val x: Long, val y: Long)

/**
 * Precomputes the interior x-ranges per y-interval for a simple
 * rectilinear polygon, enabling O(log n) rectangle-inside checks.
 */
class InteriorRanges(polygon: List<Coord>) {

    private data class YRange(val yLow: Long, val yHigh: Long, val left: Long, val right: Long)

    private val ranges: List<YRange>
    private val yMinAll: Long
    private val yMaxAll: Long

    init {
        val vertEdges = mutableListOf<VertEdge>()
        val ys = mutableSetOf<Long>()
        for (i in polygon.indices) {
            val p = polygon[i]
            val q = polygon[(i + 1) % polygon.size]
            if (p.x == q.x) {
                val yMin = minOf(p.y, q.y)
                val yMax = maxOf(p.y, q.y)
                val down = p.y < q.y
                vertEdges.add(VertEdge(p.x, yMin, yMax, down))
                ys.add(yMin)
                ys.add(yMax)
            }
        }

        val sortedYs = ys.sorted()
        val tmpRanges = mutableListOf<YRange>()

        for (k in 0 until sortedYs.size - 1) {
            val yLow = sortedYs[k]
            val yHigh = sortedYs[k + 1]
            val midY = (yLow + yHigh) / 2

            // Collect vertical edges spanning midY
            val xs = mutableListOf<Pair<Long, Boolean>>()
            for (e in vertEdges) {
                if (e.yMin <= midY && midY < e.yMax) {
                    xs.add(e.x to e.down)
                }
            }
            xs.sortBy { it.first }

            // For a CCW polygon:
            //   UP edge (decreasing y) -> interior is EAST (x > edge_x) -> crossing left-to-right = ENTER
            //   DOWN edge (increasing y) -> interior is WEST (x < edge_x) -> crossing left-to-right = EXIT
            var inside = false
            var curLeft = Long.MAX_VALUE
            for ((xVal, isDown) in xs) {
                if (!inside && !isDown) {
                    curLeft = xVal
                    inside = true
                } else if (inside && isDown) {
                    tmpRanges.add(YRange(yLow, yHigh, curLeft, xVal))
                    inside = false
                }
            }
        }

        ranges = tmpRanges
        yMinAll = if (sortedYs.isEmpty()) 0L else sortedYs.first()
        yMaxAll = if (sortedYs.isEmpty()) 0L else sortedYs.last()
    }

    fun rectInside(minX: Long, maxX: Long, minY: Long, maxY: Long): Boolean {
        // Quick reject: must be within overall y-bounds
        if (minY < yMinAll || maxY > yMaxAll) return false

        for (r in ranges) {
            // Check if this y-range overlaps (minY, maxY)
            // We use (yLow, yHigh) half-open to match the interval computation
            if (r.yHigh > minY && r.yLow < maxY) {
                if (r.left > minX || r.right < maxX) return false
            }
        }
        return true
    }

    private data class VertEdge(val x: Long, val yMin: Long, val yMax: Long, val down: Boolean)
}
