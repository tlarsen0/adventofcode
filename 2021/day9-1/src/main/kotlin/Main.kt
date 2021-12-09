import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 9.1 starting!!!!")

    val smokeMap = SmokeMap()
    File(args[0]).forEachLine {
        smokeMap.readLine(it)
    }

    smokeMap.markLowPoints()
    smokeMap.dumpMap()

    println("Risk Level: " + smokeMap.riskLevel())

    println("AOC 2021, Day 9.1 complete!!!!")
}

data class MyCoords constructor(val x : Int, val y : Int){

}

class SmokeMap {
    private val heightMap = HashMap<MyCoords, Int>()
    private var trackY = 0
    private var maxX = 0
    private var maxY = 0

    fun readLine(line : String) {
        var trackX = 0

        for(h in line) {
            heightMap[MyCoords(trackX, trackY)] = h.digitToInt()
            trackX++
        }
        trackY++
        maxX = maxX.coerceAtLeast(trackX)
        maxY = maxY.coerceAtLeast(trackY)
    }

    private val lowPoints = ArrayList<MyCoords>()

    fun markLowPoints() {
        for(y in 0 until maxY) {
            for(x in 0 until maxX) {
                val coordinates = MyCoords(x, y)
                if(lowerThanNeighbors(coordinates)) {
                    lowPoints.add(coordinates)
                }
            }
        }
        println("Found ${lowPoints.size} local low points.")
    }

    private fun lowerThanNeighbors(coordinate : MyCoords) : Boolean {
        var isLowest = true

        val height : Int = heightMap[coordinate]!!

        val aboveCoordinate = MyCoords(coordinate.x, coordinate.y - 1)
        val belowCoordinate = MyCoords(coordinate.x, coordinate.y + 1)
        val leftCoordinate  = MyCoords(coordinate.x - 1, coordinate.y)
        val rightCoordinate = MyCoords(coordinate.x + 1, coordinate.y)

        // Check above coordinate
        if(aboveCoordinate.y >= 0) {
            isLowest = heightMap[aboveCoordinate]!! > height
        } // else...this coordinate is on the upper edge of the map, above this position is always higher.

        if(belowCoordinate.y < maxY) {
            isLowest = isLowest && heightMap[belowCoordinate]!! > height
        } // else...this coordinate is on the lower edge of the map, below this position is always higher.

        if(leftCoordinate.x >= 0) {
            isLowest = isLowest && heightMap[leftCoordinate]!! > height
        } // else...this coordinate is on the left edge of the map, left of this position is always higher.

        if(rightCoordinate.x < maxX) {
            isLowest = isLowest && heightMap[rightCoordinate]!! > height
        } // else...this coordinate is on the right edge of the map, right of this position is always higher.

        return isLowest
    }

    fun dumpMap() {
        println("Map ix $maxX X $maxY :")
        for (y in 0 until maxY) {
            for (x in 0 until maxX) {
                if(lowPoints.contains(MyCoords(x, y))) {
                    // Mark the low points
                    print("*${heightMap[MyCoords(x, y)]!!}*")
                } else {
                    // Plain old point
                    print(" ${heightMap[MyCoords(x, y)]!!} ")
                }
            }
            println("")
        }
    }

    fun riskLevel() : Int {
        var risk = 0
        // There feels like there should be a way to use a stream for a solution?
        for(point in lowPoints) {
            risk += heightMap[point]!! + 1
        }

        return risk
    }
}
