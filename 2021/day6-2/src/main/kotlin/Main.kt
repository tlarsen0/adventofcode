import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 6.2 starting!!!!")

    val schoolOfFish = SchoolOfFish()
    File(args[0]).forEachLine {
        schoolOfFish.readLine(it)
    }

    for(days in 1 .. 256) {
        schoolOfFish.liveADay(days)
    }

    println("AOC 2021, Day 6.2 complete!!!!")
}

class SchoolOfFish {
    private val reproductiveCycles : HashMap<Int, Long> = HashMap()

    init {
        for(i in 0 .. 8)
            reproductiveCycles[i] = 0L
    }

    fun readLine(line: String) {
        for (s in line.split(",")) {
            val i = s.toInt()
            reproductiveCycles[i] = reproductiveCycles[i]!! + 1
        }
        println("tlarsen,L31: after load = $reproductiveCycles")
    }

    fun liveADay(days : Int) {
        val copyReproductiveCycles: HashMap<Int, Long> = HashMap(reproductiveCycles)

        for(i in 7 downTo 0) {
            reproductiveCycles[i] = copyReproductiveCycles[i + 1]!!
        }

        // new fish start at 8 spawning 1 from each
        reproductiveCycles[8] = copyReproductiveCycles[0]!!
        // fish gave birth, reset cycle to 6
        reproductiveCycles[6] = copyReproductiveCycles[0]!! + reproductiveCycles[6]!!


        var sum = 0L
        reproductiveCycles.values.forEach { sum += it }
        println("On Day $days there are $sum")
    }
}