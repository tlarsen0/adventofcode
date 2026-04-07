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

        fun fireStep(manifoldIndex: Int, beamIndex: Int) {
            // end case: if beam is off the bottom of the manifold, halt and count it.
            if (manifoldIndex == allManifolds.size) {
                completedBeams++
                if (completedBeams % 100_000_000 == 0L) {
                    println("Beam completed: ${completedBeams / 100_000_000} hundred million")
                }
                return
            }

            val theChars = allManifolds[manifoldIndex].toCharArray()

            // Start case: find 'S'
            if ((completedBeams == 0L) && (beamIndex == -1)) {
                val startIndex = theChars.indexOfFirst { it == 'S' }
                if (startIndex != -1) {
                    fireStep(manifoldIndex + 1, startIndex)
                }
                println("L37: WARN - this should not be executed")
                return
            }

            when(theChars[beamIndex]) {
                '^' -> {
                    // Left beam
                    fireStep(manifoldIndex + 1, beamIndex - 1)

                    // Right beam
                    fireStep(manifoldIndex + 1, beamIndex + 1)
                }
                else -> {
                    // Continue straight
                    fireStep(manifoldIndex + 1, beamIndex)
                }
            }
        }

        fireStep(0, -1)
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
