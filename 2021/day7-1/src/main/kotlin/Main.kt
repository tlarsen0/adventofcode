import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 7.1 starting!!!!")

    val crabCast = CrabCast()
    File(args[0]).forEachLine {
        for(pos in it.split(",")) {
            crabCast.addCrab(pos.toInt())
        }
    }

    val leastFuel = crabCast.leastEnergyUsed()
    println("Least amount of fuel spent: $leastFuel")

    println("AOC 2021, Day 7.1 complete!!!!")
}

// Evidently a group of crabs is a cast.
class CrabCast {
    private val crabs = ArrayList<Int>()
    private var minPosition = 0
    private var maxPosition = 0

    fun addCrab(position : Int) {
        crabs.add(position)
        minPosition = minPosition.coerceAtMost(position)
        maxPosition = maxPosition.coerceAtLeast(position)
    }

    fun leastEnergyUsed() : Int {
        var minEnergy = -1
        for(i in minPosition .. maxPosition) {
            var energy = 0
            for (crab in crabs) {
                energy += Math.abs(crab - i)
            }
            println("At position $i, energy used $energy")
            if(minEnergy == -1)
                minEnergy = energy
            else
                minEnergy = minEnergy.coerceAtMost(energy)
        }
        return minEnergy
    }
}