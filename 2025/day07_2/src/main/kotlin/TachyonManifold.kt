package org.tlarsen.adventofcode2025.day07.part2

/**
 * TachyonManifold models the behavior of tachyon beams fired.
 */
class TachyonManifold {
    private val allManifolds = mutableListOf<String>()

    fun add(manifold: String) {
        allManifolds.add(manifold)
    }

    /**
     * fireTachyons calculates and returns the number of timelines generated in total.
     * Uses memoization to avoid exponential time complexity.
     */
    fun fireTachyons(): Long {
        val memo = mutableMapOf<Pair<Int, Int>, Long>()

        fun fireStep(manifoldIndex: Int, beamIndex: Int): Long {
            // Check if we've already computed this position
            val key = Pair(manifoldIndex, beamIndex)
            memo[key]?.let { return it }

            // End case: if beam is off the bottom of the manifold, count it
            if (manifoldIndex == allManifolds.size) {
                return 1L
            }

            val theChars = allManifolds[manifoldIndex].toCharArray()

            val result = when(theChars[beamIndex]) {
                '^' -> {
                    // Left beam
                    val left = fireStep(manifoldIndex + 1, beamIndex - 1)
                    // Right beam
                    val right = fireStep(manifoldIndex + 1, beamIndex + 1)
                    left + right
                }
                else -> {
                    // Continue straight
                    fireStep(manifoldIndex + 1, beamIndex)
                }
            }

            memo[key] = result
            return result
        }

        return fireStep(0, findStart())
    }

    /**
     * findStart returns the index/position to start the beam, by definition it is in the 0/first row, character 'S'.
     */
    private fun findStart(): Int {
        val theChars = allManifolds[0].toCharArray()
        val startIndex = theChars.indexOfFirst { it == 'S' }
        if (startIndex != -1) {
            return startIndex
        }
        println("L37: WARN - this should not be executed")
        return -1

    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("All Manifolds:\n")
        if (allManifolds.size < 50) {
            allManifolds.forEach {
                sb.append(it).append("\n")
            }
        }
        return sb.toString()
    }
}
