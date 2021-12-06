import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 6.1 starting!!!!")

    val schoolOfFish = ArrayList<LanternFish>()
    File(args[0]).forEachLine {
        for (cycle in it.split(",")) {
            schoolOfFish.add(LanternFish(cycle.toInt()))
        }
    }

    //schoolOfFish.stream().forEach { println("tlarsen,L13: fish = ${it.reproductionCycle}") }

    for(days in 1 .. 80) {
        val newFishes = ArrayList<LanternFish>()
        //println("tlarsen,L16: day = $days")
        //schoolOfFish.stream().forEach { print("${it.reproductionCycle} ") }
        for(fish in schoolOfFish) {
            if(fish.liveADay()) {
                newFishes.add(LanternFish(8))
            }
        }
        //println("")
        schoolOfFish.addAll(newFishes)
    }

    println("Fishes in the school: ${schoolOfFish.size}")

    println("AOC 2021, Day 6.1 complete!!!!")
}

class LanternFish constructor(cycle:Int) {
    var reproductionCycle: Int private set

    init {
        reproductionCycle = cycle
    }

    fun liveADay() : Boolean {
        reproductionCycle--
        if(reproductionCycle < 0) {
            reproductionCycle = 6
            return true
        }
        return false
    }
}