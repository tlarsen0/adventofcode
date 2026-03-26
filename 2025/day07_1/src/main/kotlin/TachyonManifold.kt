package org.tlarsen.adventofcode2025.day07.part1

/**
 * Represents a manifold of tachyons for Advent of Code 2025, Day 7.
 * This class processes a series of manifolds to calculate beam splits.
 */
class TachyonManifold {
    private val allManifolds = ArrayList<String>()

    /**
     * Adds a new manifold string to the collection.
     *
     * @param manifold The string representation of the manifold.
     */
    fun add(manifold: String) {
        allManifolds.add(manifold)
    }

    /**
     * Processes all added manifolds and calculates the total number of beam splits.
     *
     * @return The total count of beam splits encountered across all manifolds.
     */
    fun fireTachyons() : Int {
        var splitCount = 0
        val beamIndexes = HashSet<Int>()
        for (manifold in allManifolds) {
            splitCount += fireStep(manifold, beamIndexes)
        }
        return splitCount
    }

    /**
     * Processes a single manifold step, updating the beam positions and counting splits.
     *
     * @param manifold The current manifold string being processed.
     * @param beamIndexes A set of current beam positions (indices), which is updated in-place.
     * @return The number of new splits encountered in this step.
     */
    fun fireStep(manifold: String, beamIndexes: HashSet<Int>) : Int {
        // Special case: if beamIndexes is empty, it's the starting manifold.
        // Locate 'S' to initialize the beam position.
        if(beamIndexes.isEmpty()) {
            for((index, c) in manifold.withIndex()) {
                if(c == 'S') {
                    beamIndexes.add(index)
                    return 0
                }
            }
        }

        var splitCount = 0
        // We iterate based on a snapshot of the current beams to ensure consistent behavior
        // when beams are added or removed during the step.
        val currentBeams = beamIndexes.toSet()
        for((index, c) in manifold.withIndex()) {
            // Split encountered if a beam is at a '^' position.
            if((c == '^') && currentBeams.contains(index)) {
                beamIndexes.remove(index)
                beamIndexes.add(index - 1)
                beamIndexes.add(index + 1)
                splitCount++
            }
        }
        return splitCount
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append("All Manifolds:\n")
        allManifolds.forEach {
            sb.append(it).append("\n")
        }
        return sb.toString()
    }
}
