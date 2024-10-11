import java.io.File
import java.math.BigInteger

fun main(args: Array<String>) {
    println("AOC 2023, Day 6, Part 2 starting!!!!")

    val time = Time()
    val distance = Distance()
    File(args[0]).forEachLine {
        println("Data read-- $it")
        val theSplit = it.split("""\W+""".toRegex())

        if(theSplit.first() == "Time") {
            val rebuildTime = ArrayList<Int>()

            for(split in theSplit) {
                if(split == theSplit.first()) {
                    // skip the first since it is "Time"
                    continue
                }
                rebuildTime.add(split.toInt())
            }
            time.raceTime = rebuildValue(rebuildTime)
        } else {
            val rebuildDistance = ArrayList<Int>()
            for(split in theSplit) {
                if(split == theSplit.first()) {
                    // skp the "Distance"
                    continue
                }
                rebuildDistance.add(split.toInt())
            }
            distance.raceDistance = rebuildValue(rebuildDistance)
        }
    }
    val timeDistance = TimeDistance(time, distance)

    val answer = timeDistance.calculateWaysToBeat()

    println("The answer: $answer")

    println("AOC 2023, Day 6, Part 2 completed!!!")
}

fun rebuildValue(stringValues:ArrayList<Int>):BigInteger {
    val theString:StringBuilder = StringBuilder()
    stringValues.forEach { theString.append(it) }
    return theString.toString().toBigInteger()
}

class Time {
    var raceTime:BigInteger = BigInteger.valueOf(0L)
}

class Distance {
    var raceDistance:BigInteger = BigInteger.valueOf(0L)
}

class TimeDistance(private val time: Time, private val distance: Distance) {
    fun calculateWaysToBeat():BigInteger {
        return runRace(time.raceTime, distance.raceDistance)
    }

    private fun runRace(raceTime: BigInteger, raceDistance: BigInteger):BigInteger {
        var winCount = BigInteger.valueOf(0L)
        for(t:Long in 1.. raceTime.toLong()) {
            val runTime = raceTime.minus(BigInteger.valueOf(t))
            //val calculatedDistance = t * runTime
            val calculatedDistance = BigInteger.valueOf(t).multiply(runTime)
            if(calculatedDistance > raceDistance) {
                winCount++
            }
        }
        return winCount
    }
}
