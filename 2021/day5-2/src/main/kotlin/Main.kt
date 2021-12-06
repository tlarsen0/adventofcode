import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 5.2 starting!!!!")

    val ventMap = VentMap()
    File(args[0]).forEachLine {
        ventMap.readLine(it)
    }

    ventMap.markupMap()
    //ventMap.displayMap()
    println("The number of places greater than 2: " + ventMap.heightCheck(2))


    println("AOC 2021, Day 5.1 complete!!!!")
}

data class MyPair constructor(val X : Int, val Y : Int) {

}

class VentData(var start: MyPair, var end: MyPair) {

}

class VentMap {
    private val vents = ArrayList<VentData>()
    private val theMap = HashMap<MyPair, Int>()
    var horizontalVents = 0
    var verticalVents = 0
    var diagonalVents = 0

    fun readLine(line : String) {
        val split = line.split("->".toRegex())
        val splitSplit0 = split[0].split(",")
        val splitSplit1 = split[1].split(",")
        val startPoint = MyPair(splitSplit0[0].trim().toInt(), splitSplit0[1].trim().toInt())
        val endPoint = MyPair(splitSplit1[0].trim().toInt(), splitSplit1[1].trim().toInt())

        vents.add(VentData(startPoint, endPoint))
    }

    fun heightCheck(testHeight : Int) : Int{
        return theMap.values.stream().filter { it >= testHeight }.count().toInt()
    }

    fun displayMap() {
        println("Displaying map:")
        println("  Horizontal Vents: $horizontalVents")
        println("    Vertical Vents: $verticalVents")
        println("    Diagonal Vents: $diagonalVents")
        println("       Total Vents: ${vents.size}")
        for(y in 0..999) {
            for(x in 0..999) {
                print(" " + theMap[MyPair(x, y)])
            }
            println("")
        }
    }

    fun markupMap() {
        // initialize theMap
        for(x in 0..999) {
            for(y in 0..999) {
                theMap[MyPair(x, y)] = 0
            }
        }
        for(vent in vents) {
            if(vent.start.X == vent.end.X) { // vent runs vertical
                val yLower = Math.min(vent.start.Y, vent.end.Y)
                val yUpper = Math.max(vent.start.Y, vent.end.Y)
                for(y in yLower.. yUpper) {
                    val p = MyPair(vent.start.X, y)
                    theMap[p] = theMap[p]!! + 1
                }
                verticalVents++
            } else if(vent.start.Y == vent.end.Y) { // vent runs horizontal
                val xLower = Math.min(vent.start.X, vent.end.X)
                val xUpper = Math.max(vent.start.X, vent.end.X)
                for(x in xLower .. xUpper) {
                    val p = MyPair(x, vent.start.Y)
                    theMap[p] = theMap[p]!! + 1
                }
                horizontalVents++
            } else { // vent runs diagonal.
                var xStep = 1
                if(vent.start.X > vent.end.X)
                    xStep = -1

                var yStep = 1
                if(vent.start.Y > vent.end.Y)
                    yStep = -1

                var x = vent.start.X
                var y = vent.start.Y
                var steps = Math.max(vent.start.X, vent.end.X) - Math.min(vent.start.X, vent.end.X)
                //while((x != vent.end.X) && (y != vent.end.Y)) {
                while(steps > -1) {
                    val p = MyPair(x, y)
                    theMap[p] = theMap[p]!! + 1
                    x += xStep
                    y += yStep
                    steps--
                }

                diagonalVents++
            }
        }
    }
}