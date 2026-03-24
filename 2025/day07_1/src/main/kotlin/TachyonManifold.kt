package org.tlarsen.adventofcode2025.day07.part1

class TachyonManifold {
    private val allManifolds = ArrayList<String>()
    fun add(manifold: String) {
        allManifolds.add(manifold)
    }

    fun fireTachyons() : Int {
        var splitCount = 0
        val beamIndexes = HashSet<Int>()
        for (manifold in allManifolds) {
            splitCount += fireStep(manifold, beamIndexes)
        }
        return splitCount
    }

    /**
     * fireStep calculates beams and splits.
     * returns number of new splits encountered
     */
    fun fireStep(manifold: String, beamIndexes: HashSet<Int>) : Int {
        // special case: if beamIndex is empty, then it is the start! find S and use that
        if(beamIndexes.isEmpty()) {
            for((index, c) in manifold.toCharArray().withIndex()) {
                if(c == 'S') {
                    beamIndexes.add(index)
                    return 0
                }
            }
        }

        var splitCount = 0
        for((index, c) in manifold.toCharArray().withIndex()) {
            // split encountered!
            if((c == '^') && beamIndexes.contains(index)) {
                // remove the beam at index
                beamIndexes.remove(index)
                // add beams before and after index
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