import java.io.File

fun main(args: Array<String>) {
    println("AOC 2024, Day 6, Part 2 starting!!!!")

    val theMap = TheMap(ArrayList())

    var x = 0
    var y = 0
    var maxX = 0
    var maxY = 0
    File(args[0]).forEachLine {
        for(c in it) {
            when(c) {
                MapSpace.EMPTY.value -> theMap.mapNodes.add(MapNode(MapSpace.EMPTY, x, y))
                MapSpace.OBSTRUCTED.value -> theMap.mapNodes.add(MapNode(MapSpace.OBSTRUCTED, x, y))
                MapSpace.GUARD_UP.value -> theMap.mapNodes.add(MapNode(MapSpace.GUARD_UP, x, y))
                MapSpace.GUARD_LEFT.value -> theMap.mapNodes.add(MapNode(MapSpace.GUARD_LEFT, x, y))
                MapSpace.GUARD_RIGHT.value -> theMap.mapNodes.add(MapNode(MapSpace.GUARD_RIGHT, x, y))
                MapSpace.GUARD_DOWN.value -> theMap.mapNodes.add(MapNode(MapSpace.GUARD_DOWN, x, y))
            }
            x++
        }
        y++
        maxX = maxX.coerceAtLeast(x)
        maxY = maxY.coerceAtLeast(y)
        x = 0
    }

    println("Initializing map...")
    theMap.initialize(maxX, maxY)
    theMap.printMap()

    var inLoop = 0
    val loopResults = ArrayList<MapNode>()
    var outOfBounds = 0

    println("Placing temporal obstacle")
    for(obstX in 0..maxX) {
        for (obstY in 0..maxY) {
            val obstacle = theMap.mapNodes.find { it.x == obstX && it.y == obstY && it.theSpace == MapSpace.EMPTY }

            if (obstacle != null) {
                println("Temporal obstruction placed at $obstX, $obstY")
                obstacle.theSpace = MapSpace.OBSTRUCTED_TEMPORAL

                println("Running guard...")
                val haltReason = theMap.runGuard()
                if(haltReason == Halt.LIMIT_REACHED) {
                    println("Guard stuck in a loop?")
                    loopResults.add(obstacle)
                    inLoop++
                } else if(haltReason == Halt.OUT_OF_BOUNDS) {
                    // "Guard out of bounds, ignore it"
                    outOfBounds++
                } else {
                    // "Something else happened to stop the guard!!!!"
                }

                println("Resetting and removing obstruction at $obstX, $obstY")
                theMap.resetMap()
            }
        }
    }


    println("Final results: Obstructions that cause a loop: $inLoop, Out of bounds: $outOfBounds")

    print("Here are the obstacles that cause a loop:")
    loopResults.forEach { print(" (${it.x}, ${it.y}),") }
    println("")

    println("AOC 2024, Day 6, Part 2 completed!!!")
}

enum class MapSpace (val value: Char) {
    EMPTY('.'),
    OBSTRUCTED('#'),
    GUARD_UP('^'),
    GUARD_LEFT('<'),
    GUARD_RIGHT('>'),
    GUARD_DOWN('v'),
    VISITED('X'),
    OBSTRUCTED_TEMPORAL('O')
}

enum class Halt {
    CONTINUING,
    OUT_OF_BOUNDS,
    LIMIT_REACHED
}

data class MapNode (var theSpace:MapSpace, val x:Int, val y:Int)

class TheMap (val mapNodes:ArrayList<MapNode>) {
    private var theGuard:MapNode? = null
    private var minX: Int = 0
    private var minY: Int = 0
    private var maxX: Int = 0
    private var maxY: Int = 0
    private val originalMap = ArrayList<MapNode>()

    fun initialize(setMaxX: Int, setMaxY: Int) {
        if(theGuard == null) {
            theGuard = findGuard()
        }

        maxX = setMaxX
        maxY = setMaxY

        // store a copy of the original map to allow multiple resets and inspections.
        for(node in mapNodes) {
            originalMap.add(MapNode(node.theSpace, node.x, node.y))
        }
    }

    fun resetMap() {
        // remove all nodes
        mapNodes.clear()
        // copy in original map
        for(oldMapNode in originalMap) {
            mapNodes.add(MapNode(oldMapNode.theSpace, oldMapNode.x, oldMapNode.y))
        }
        // reset guard location back to where it is in the original map
        theGuard = findGuard()
        println("Map reset!")
    }

    fun runGuard():Halt {
        var halt = Halt.CONTINUING
        var rawStepCount = 0
        var previousDistinctCount = 0
        var distinctCount = 0

        while(halt == Halt.CONTINUING) {
            halt = if(nextStepGuard()) Halt.OUT_OF_BOUNDS else Halt.CONTINUING
            rawStepCount++
            val nowDistinctCount = countDistinctPositionsTraveled()

            if(nowDistinctCount == previousDistinctCount) {
                distinctCount++
            } else {
                distinctCount = 0
            }
            // Why 5235? 5233 is the path steps in the nominal case.
            if((distinctCount > 5235) && (distinctCount > nowDistinctCount)) {
                halt = Halt.LIMIT_REACHED
            }

            previousDistinctCount = nowDistinctCount
        }
        println("Finish walking, step take: $rawStepCount")
        return halt
    }

    fun countDistinctPositionsTraveled(): Int {
        var count = 0
        for(node in mapNodes) {
            if(node.theSpace == MapSpace.VISITED) {
                count++
            }
        }

        return count + 1
    }

    fun printMap() {
        for(y in minY..maxY - 1) {
            for (x in minX..maxX - 1) {
                val node = mapNodes.find { it.x == x && it.y == y }
                print(node?.theSpace?.value)
            }
            println("")
        }
    }

    fun nextStepGuard(): Boolean {
        val oldGuardData = theGuard
        // given the direction of the guard, move one space in that direction.
        // - don't move if there is an obstruction in that direction but turn right instead.
        if (isNextOutOfBounds()) {
            // out of bounds, halt guard
            return true // halt
        }

        if(isNextSpaceBlocked()) {
            // if next step is bocked then have guard turn right
            turnGuardRight()
            return false // continue walking
        }

        // Move guard one step forward
        moveGuardForward()
        // mark old space with X/visited
        mapNodes.find { it.x == oldGuardData?.x && it.y == oldGuardData.y }?.theSpace = MapSpace.VISITED
        return false
    }

    // moveGuardForward will return true if it can move forward.
    //  If guard cannot move forward due to obstruction then the position will not change and return false.
    fun moveGuardForward() {

        val guardDirection = theGuard?.theSpace

        theGuard = when(theGuard?.theSpace) {
            MapSpace.GUARD_UP -> mapNodes.find { it.x == theGuard?.x && it.y == theGuard?.y?.minus(1) }
            MapSpace.GUARD_RIGHT -> mapNodes.find { it.x == theGuard?.x?.plus(1) && it.y == theGuard?.y }
            MapSpace.GUARD_DOWN -> mapNodes.find { it.x == theGuard?.x && it.y == theGuard?.y?.plus(1) }
            MapSpace.GUARD_LEFT -> mapNodes.find { it.x == theGuard?.x?.minus(1) && it.y == theGuard?.y }
            else -> return // do nothing
        }

        if (guardDirection != null) {
            theGuard?.theSpace = guardDirection
        }
    }

    fun isNextSpaceBlocked(): Boolean {
        return when(theGuard?.theSpace) {
            MapSpace.GUARD_UP -> checkSpace(theGuard?.x, theGuard?.y?.minus(1))
            MapSpace.GUARD_RIGHT -> checkSpace(theGuard?.x?.plus(1), theGuard?.y)
            MapSpace.GUARD_DOWN -> checkSpace(theGuard?.x, theGuard?.y?.plus(1))
            MapSpace.GUARD_LEFT -> checkSpace(theGuard?.x?.minus(1), theGuard?.y)
            else -> false // if none of the others then okay to move forward...??
        }
    }

    fun checkSpace(x: Int?, y: Int?): Boolean {
        val node = mapNodes.find { it.x == x && it.y == y }
        return node?.theSpace == MapSpace.OBSTRUCTED || node?.theSpace == MapSpace.OBSTRUCTED_TEMPORAL
    }

    fun isNextOutOfBounds(): Boolean {
        return when(theGuard?.theSpace) {
            MapSpace.GUARD_UP -> theGuard?.y?.minus(1)!! < 0
            MapSpace.GUARD_DOWN -> theGuard?.y?.plus(1)!! >= maxY
            MapSpace.GUARD_LEFT -> theGuard?.x?.minus(1)!! < 0
            MapSpace.GUARD_RIGHT -> theGuard?.x?.plus(1)!! >= maxX
            else -> false
        }
    }

    fun turnGuardRight() {
        when(theGuard?.theSpace) {
            MapSpace.GUARD_UP -> theGuard?.theSpace = MapSpace.GUARD_RIGHT
            MapSpace.GUARD_RIGHT -> theGuard?.theSpace = MapSpace.GUARD_DOWN
            MapSpace.GUARD_DOWN -> theGuard?.theSpace = MapSpace.GUARD_LEFT
            MapSpace.GUARD_LEFT -> theGuard?.theSpace = MapSpace.GUARD_UP
            else -> return
        }
    }

    fun findGuard():MapNode? {
        for(node in mapNodes) {
            if((node.theSpace == MapSpace.GUARD_UP) || (node.theSpace == MapSpace.GUARD_LEFT) || (node.theSpace == MapSpace.GUARD_RIGHT) || (node.theSpace == MapSpace.GUARD_DOWN)) {
                return node
            }
        }
        return null
    }
}
