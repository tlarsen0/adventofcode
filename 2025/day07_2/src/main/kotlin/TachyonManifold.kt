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
     */
    fun fireTachyons(): Long {
        var completedBeams = 0L

        fun fireStep(manifoldIndex: Int, currBeam: MutableList<Int>) {
            if (manifoldIndex == allManifolds.size) {
                completedBeams++
                if (completedBeams % 100_000_000 == 0L) {
                    println("Beam completed: ${completedBeams / 1_000_000} hundred million")
                }
                return
            }

            val manifold = allManifolds[manifoldIndex]
            val chars = manifold.toCharArray()

            // Start case: find 'S'
            if (currBeam.isEmpty()) {
                val startIndex = chars.indexOfFirst { it == 'S' }
                if (startIndex != -1) {
                    currBeam.add(startIndex)
                    fireStep(manifoldIndex + 1, currBeam)
                    currBeam.removeLast()
                }
                return
            }

            val beamPos = currBeam.last()
            when (chars[beamPos]) {
                '^' -> {
                    // Left beam
                    currBeam.add(beamPos - 1)
                    fireStep(manifoldIndex + 1, currBeam)
                    currBeam.removeLast()

                    // Right beam
                    currBeam.add(beamPos + 1)
                    fireStep(manifoldIndex + 1, currBeam)
                    currBeam.removeLast()
                }
                else -> {
                    // Continue straight
                    currBeam.add(beamPos)
                    fireStep(manifoldIndex + 1, currBeam)
                    currBeam.removeLast()
                }
            }
        }

        fireStep(0, mutableListOf())
        return completedBeams
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
