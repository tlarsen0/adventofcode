import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 17.2 starting!!!!")

    val probeCourseMap = ProbeCourseMap()
    // args[0] should read /Users/tlarsen/src/adventofcode/2021/DAY-PART/src/main/resources
    File(args[0]).forEachLine {
        probeCourseMap.addTargetArea(it)
    }

    val record = HashMap<MyCoord, Int>()
    for(y in -1000..1000) {
        for (x in 0..1000) {
            val h = probeCourseMap.launchProbe(x, y, MyCoord(0, 0), 0)
            if (h != Int.MIN_VALUE) {
                record[MyCoord(x, y)] = h
            }
        }
    }

    /*
    println("There were ${record.size} probe launches that hit the target area.")
    val maxY = record.values.maxByOrNull { it }
    println("The maximum Y: $maxY")
     */

    println("Dumping all of the initial velocities that hit the target area:")
    var rowCount = 0
    for(r in record) {
        print(" (${r.key.x}, ${r.key.y}) ")
        rowCount++
        if(rowCount > 10) {
            rowCount = 0
            println()
        }
    }
    println()

    println("There were ${record.size} probe launches that hit the target area.")

    println("AOC 2021, Day 17.2 complete!!!!")
}

data class MyCoord constructor(val x:Int, val y:Int)

class ProbeCourseMap {
    private val targetArea = ArrayList<MyCoord>()

    // This is a little ugly but it works
    fun addTargetArea(line: String) {
        val split = line.split("\\w=".toRegex())
        val splitComma = split[1].removeSuffix(", ")
        val xRange = splitComma.split("..")
        val yRange = split[2].split("..")

        targetArea.add(MyCoord(xRange[0].toInt(), yRange[1].toInt()))
        targetArea.add(MyCoord(xRange[1].toInt(), yRange[0].toInt()))
    }

    fun launchProbe(xVel: Int, yVel: Int, probePosition: MyCoord, maxHeight: Int) : Int {
        // Move probe forward.
        val newProbePosition = MyCoord(probePosition.x + xVel, probePosition.y + yVel)
        // Check newProbePosition if it is in the target
        if((newProbePosition.x >= targetArea[0].x) && (newProbePosition.x <= targetArea[1].x) &&
            (newProbePosition.y <= targetArea[0].y) && (newProbePosition.y >= targetArea[1].y)) {
            //println("Probe hit the target!")
            return Math.max(maxHeight, newProbePosition.y)
        }

        // Undershot, overshot, or now too low to hit: general miss
        if(newProbePosition.y < targetArea[1].y) {
            //println("FAIL: probe missed, last position ${newProbePosition.x}, ${newProbePosition.y}")
            return Int.MIN_VALUE
        }
        // Otherwise, next step with drag rules.
        var newXVel = xVel
        if(xVel > 0) {
            newXVel--
        } else if (xVel < 0) {
            newXVel++
        } // else at 0 and shouldn't change
        val newYVel = yVel - 1
        return launchProbe(newXVel, newYVel, newProbePosition, Math.max(maxHeight, newProbePosition.y))
    }
}
