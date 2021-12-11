import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 10.1 starting!!!!")

    var xCoord = 0
    var yCoord = 0

    val squidSquad = SquidSquad()
    File(args[0]).forEachLine {
        for(c in it) {
            val squid = Squid(c.digitToInt())
            squidSquad.add(squid, MyCoord(xCoord, yCoord))
            xCoord++
        }
        yCoord++
        xCoord = 0
    }

    squidSquad.dumpMap(0)

    var flashes = 0

    for(step in 1 .. 100) {
        squidSquad.stepForward()
        squidSquad.applyFlash()
        flashes += squidSquad.applyRest()
        squidSquad.dumpMap(step)
    }

    println("Flashes seen: $flashes")

    println("AOC 2021, Day 10.1 complete!!!!")
}

class Squid constructor(initialEnergy : Int) {
    var energyLevel : Int

    init {
        energyLevel = initialEnergy
    }

    fun isFlashing() : Boolean {
        return energyLevel > 9
    }
}

data class MyCoord constructor(val x : Int, val y : Int){
}

class SquidSquad {
    private val squidMap = HashMap<MyCoord, Squid>()

    fun add(newSquid: Squid, coord: MyCoord) {
        squidMap[coord] = newSquid
    }

    fun dumpMap(step : Int = 0) {
        println("Map for Step $step")
        for(y in 0 until  10) { // cheesy 10 x 10
            for(x in 0 until  10) {
                if(squidMap[MyCoord(x, y)]!!.energyLevel == 0) {
                    print("*${squidMap[MyCoord(x, y)]!!.energyLevel}*")
                } else {
                    print(" ${squidMap[MyCoord(x, y)]!!.energyLevel} ")
                }
            }
            println(" ")
        }
    }

    private var flashingSquid = ArrayList<MyCoord>()
    // increase
    fun stepForward() {
        flashingSquid.clear()

        // Increase the energy of all squids
        for(y in 0 until 10) {
            for(x in 0 until  10) {
                squidMap[MyCoord(x, y)]!!.energyLevel++
            }
        }
    }

    private fun increaseNeighbors(coordinate: MyCoord) {
        // increase the energy of surrounding squids from that squid's coord
        val aboveLeftCoord = MyCoord(coordinate.x - 1, coordinate.y - 1)
        val aboveCoordinate = MyCoord(coordinate.x, coordinate.y - 1)
        val aboveRightCoord = MyCoord(coordinate.x + 1, coordinate.y - 1)

        val belowLeftCoord = MyCoord(coordinate.x - 1, coordinate.y + 1)
        val belowCoordinate = MyCoord(coordinate.x, coordinate.y + 1)
        val belowRightCoord = MyCoord(coordinate.x + 1, coordinate.y + 1)

        val leftCoordinate = MyCoord(coordinate.x - 1, coordinate.y)
        val rightCoordinate = MyCoord(coordinate.x + 1, coordinate.y)

        if ((aboveLeftCoord.x >= 0) && (aboveLeftCoord.y >= 0)) {
            squidMap[aboveLeftCoord]!!.energyLevel++
        } // else there is no squid off the map.

        if (aboveCoordinate.y >= 0) {
            squidMap[aboveCoordinate]!!.energyLevel++
        } // else there is no squid off the map.

        if ((aboveRightCoord.x <= 9) && (aboveRightCoord.y >= 0)) {
            squidMap[aboveRightCoord]!!.energyLevel++
        } // else there is no squid off the map.

        if (leftCoordinate.x >= 0) {
            squidMap[leftCoordinate]!!.energyLevel++
        } // else there is no squid off the map.

        if (rightCoordinate.x <= 9) {
            squidMap[rightCoordinate]!!.energyLevel++
        } // else there is no squid off the map.

        if ((belowLeftCoord.x >= 0) && (belowLeftCoord.y <= 9)) {
            squidMap[belowLeftCoord]!!.energyLevel++
        }

        if ((belowCoordinate.y <= 9)) {
            squidMap[belowCoordinate]!!.energyLevel++
        }

        if ((belowRightCoord.x <= 9) && (belowRightCoord.y <= 9)) {
            squidMap[belowRightCoord]!!.energyLevel++
        }
    }

    fun applyFlash() {
        val flashingSquidCount = flashingSquid.count()
        for(y in 0 until 10) {
            for(x in 0 until 10) {
                val squid = squidMap[MyCoord(x, y)]

                // Add squid's coord if it has enough energy and squid's coordinate if it hasn't already been on the list
                if(squid!!.isFlashing() && !flashingSquid.contains(MyCoord(x, y))) {
                    flashingSquid.add(MyCoord(x, y))
                    increaseNeighbors(MyCoord(x, y))
                }
            }
        }

        // More squid are flashing than before, rerun apply flash
        if(flashingSquidCount != flashingSquid.count())
            applyFlash()
    }

    fun applyRest() : Int {
        for(coord in flashingSquid) {
            squidMap[coord]!!.energyLevel = 0
        }
        return flashingSquid.size
    }
}