import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 9.2 starting!!!!")

    val smokeMap = SmokeMap()
    File(args[0]).forEachLine {
        smokeMap.readLine(it)
    }

    smokeMap.markLowPoints()
    smokeMap.markBasins()

    smokeMap.dumpMap()
    println("Risk Level: " + smokeMap.riskLevel())

    println("Three Largest Basins Multiplied: " + smokeMap.getThreeLargestBasins())

    println("AOC 2021, Day 9.2 complete!!!!")
}

data class MyCoords constructor(val x : Int, val y : Int){

}

class Basin {
    private val coords = ArrayList<MyCoords>()

    fun addCoord(coordinate: MyCoords) {
        coords.add(coordinate)
    }

    fun alreadyVisited(coordinate: MyCoords) : Boolean {
        return coords.contains(coordinate)
    }

    fun getSize() : Int {
        return coords.size
    }
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

    private val basins = ArrayList<Basin>()

    fun markBasins() {
        // Start at low points and extend outward.
        for(point in lowPoints) {
            val b = Basin()
            markBasin(point, b)
            basins.add(b)
        }
    }

    fun getThreeLargestBasins() : Int {
        var answer = 0
        var count = 0
        for(b in basins.sortedWith { o1, o2 -> o2.getSize() - o1.getSize() }) {
            if(answer == 0) {
                answer = b.getSize()
            } else {
                answer *= b.getSize()
            }
            if (count == 2) {
                break
            }
            count++
        }
        return answer
    }

    private fun markBasin(coordinate: MyCoords, basin: Basin) {
        val height = heightMap[coordinate]
        if(height == 9) {
            // if the height is 9, it is a border of a basin so do nothing and return
            return
        }
        // check redundancy/already checked
        if(basin.alreadyVisited(coordinate)) {
            return
        }


        basin.addCoord(coordinate)

        val aboveCoordinate = MyCoords(coordinate.x, coordinate.y - 1)
        val belowCoordinate = MyCoords(coordinate.x, coordinate.y + 1)
        val leftCoordinate  = MyCoords(coordinate.x - 1, coordinate.y)
        val rightCoordinate = MyCoords(coordinate.x + 1, coordinate.y)

        // Check above coordinate
        if(aboveCoordinate.y >= 0) {
            markBasin(aboveCoordinate, basin)
        }
        if(belowCoordinate.y < maxY) {
            markBasin(belowCoordinate, basin)
        }
        if(leftCoordinate.x >= 0) {
            markBasin(leftCoordinate, basin)
        }
        if(rightCoordinate.x < maxX) {
            markBasin(rightCoordinate, basin)
        }
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

