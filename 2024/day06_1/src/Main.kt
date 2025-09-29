import java.io.File

fun main(args: Array<String>) {
    println("AOC 2024, Day 6, Part 1 starting!!!!")

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

    println("Running guard...")
    theMap.runGuard()

    println("Final map:")
    theMap.printMap()

    println("Final distinct spaces visited: ${theMap.countDistinctPositionsTraveled()}")

    println("AOC 2024, Day 6, Part 1 completed!!!")
}

enum class MapSpace (val value: Char) {
    EMPTY('.'),
    OBSTRUCTED('#'),
    GUARD_UP('^'),
    GUARD_LEFT('<'),
    GUARD_RIGHT('>'),
    GUARD_DOWN('v'),
    VISITED('X')
}

data class MapNode (var theSpace:MapSpace, val x:Int, val y:Int)

class TheMap (val mapNodes:ArrayList<MapNode>) {
    private var theGuard:MapNode? = null
    private var minX: Int = 0
    private var minY: Int = 0
    private var maxX: Int = 0
    private var maxY: Int = 0


    fun initialize(setMaxX: Int, setMaxY: Int) {
        if(theGuard == null) {
            theGuard = findGuard()
        }

        maxX = setMaxX
        maxY = setMaxY
    }

    fun runGuard() {
        var halt = false
        var rawStepCount = 0
        while(!halt) {
            halt = nextStepGuard()
            rawStepCount++
        }
        println("Finish walking, step take: $rawStepCount")
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
            //MapSpace.GUARD_DOWN -> theGuard?.y = theGuard?.y?.plus(1)
            MapSpace.GUARD_DOWN -> mapNodes.find { it.x == theGuard?.x && it.y == theGuard?.y?.plus(1) }
            //MapSpace.GUARD_LEFT -> theGuard?.x = theGuard?.x?.minus(1)
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
        return node?.theSpace == MapSpace.OBSTRUCTED
    }

    fun isNextOutOfBounds(): Boolean {
        return when(theGuard?.theSpace) {
            //MapSpace.GUARD_UP -> outOfBounds = checkSpace(theGuard?.x, theGuard?.y?.minus(1))
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
