import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 5.1 starting!!!!")

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

class VentData(val s: MyPair, val e: MyPair) {
    var start : MyPair = s
    var end : MyPair = e
}

class VentMap {
    private val vents = ArrayList<VentData>()
    private val theMap = HashMap<MyPair, Int>()

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
            // vent runs vertical
            if(vent.start.X == vent.end.X) {
                val yLower = Math.min(vent.start.Y, vent.end.Y)
                val yUpper = Math.max(vent.start.Y, vent.end.Y)
                for(y in yLower.. yUpper) {
                    val p = MyPair(vent.start.X, y)
                    theMap[p] = theMap[p]!! + 1
                }
            }
            // vent runs horizontal
            if(vent.start.Y == vent.end.Y) {
                val xLower = Math.min(vent.start.X, vent.end.X)
                val xUpper = Math.max(vent.start.X, vent.end.X)
                for(x in xLower .. xUpper) {
                    val p = MyPair(x, vent.start.Y)
                    theMap[p] = theMap[p]!! + 1
                }
            }
        }
    }
}