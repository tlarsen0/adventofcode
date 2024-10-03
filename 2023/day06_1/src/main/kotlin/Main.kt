import java.io.File

fun main(args: Array<String>) {
    println("AOC 2023, Day 6, Part 1 starting!!!")

    val time = Time()
    val distance = Distance()
    File(args[0]).forEachLine {
        println("Data read-- $it")
        val theSplit = it.split("""\W+""".toRegex())

        if(theSplit.first() == "Time") {
            for(split in theSplit) {
                if(split == theSplit.first()) {
                    // skip the first since it is "Time"
                    continue
                }
                time.raceTimes.add(split.toInt())
            }
        } else {
            for(split in theSplit) {
                if(split == theSplit.first()) {
                    continue
                }
                distance.raceDistances.add(split.toInt())
            }
        }
    }
    val timeDistance = TimeDistance(time, distance)

    val answer = timeDistance.calculateWaysToBeat()

    println("The answer: $answer")

    println("AOC 2023, Day 6, Part 1 completed!!!")
}

class Time {
    val raceTimes = ArrayList<Int>()
}

class Distance {
    val raceDistances = ArrayList<Int>()
}

class TimeDistance(private val time: Time, private val distance: Distance) {
    fun calculateWaysToBeat():Long {
        var winCount = 1L
        for(i in 0..<time.raceTimes.count()) {
            winCount *= runRace(time.raceTimes[i], distance.raceDistances[i])
        }
        return winCount
    }

    private fun runRace(raceTime: Int, raceDistance: Int):Long {
        var winCount = 0L
        for(t in 1.. raceTime) {
            val runTime = raceTime - t
            val calculatedDistance = t * runTime
            if(calculatedDistance > raceDistance) {
                winCount++
            }
        }
        return winCount
    }
}
