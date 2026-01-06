import java.io.File

fun main(argv:Array<String>) {
    println("AOC 2025, Day 4, Part 1 starting!!!!")
    println("Details of problem are here: https://adventofcode.com/2025/day/4")

    val thePaperRollStack = PaperRollStack()

    File(argv[0]).forEachLine {
        thePaperRollStack.add(it)
    }

    val freePaperRolls = thePaperRollStack.getFreePaperRolls()

    println("Number Free Paper Rolls: ${freePaperRolls.size}")

    println("AOC 2025, Day 4, Part 1 completed!!!")
}

/**
 * PaperRollStack models the stack of paper rolls.
 * - Data is added with "add" with "@" being rolls of paper, "." spaces
 * - adjacentRollCount is used to count adjacent rolls
 */
class PaperRollStack {
    val paperRollStack = ArrayList<String>()

    fun add(paperRollRow: String) {
        paperRollStack.add(paperRollRow)
    }

    fun getFreePaperRolls() : ArrayList<Pair<Int, Int>> {
        val freePaperRolls = ArrayList<Pair<Int, Int>>()

        for(y in 0..<paperRollStack.size) {
            for(x in 0..<paperRollStack[0].length) {
                val c = paperRollStack[y][x]
                if(c == '@' && adjacentRollCount(x, y) < 4) {
                    freePaperRolls.add(Pair(x, y))
                }
            }
        }

        return freePaperRolls
    }

    fun adjacentRollCount(x: Int, y: Int) : Int {
        val maxX = paperRollStack[0].length - 1
        val maxY = paperRollStack.size - 1

        val upperLeft : Int = if(x > 0 && y > 0 && paperRollStack[y - 1][x - 1] == '@') 1 else 0
        val upperMid : Int = if(y > 0 && paperRollStack[y - 1][x] == '@') 1 else 0
        val upperRight : Int = if(x < maxX && y > 0 && paperRollStack[y - 1][x + 1] == '@') 1 else 0

        val midLeft : Int = if(x > 0 && paperRollStack[y][x - 1] == '@') 1 else 0
        val midRight : Int = if(x < maxX && paperRollStack[y][x + 1] == '@') 1 else 0

        val lowerLeft : Int = if(x > 0 && y < maxY && paperRollStack[y + 1][x - 1] == '@') 1 else 0
        val lowerMid : Int = if(y < maxY && paperRollStack[y + 1][x] == '@') 1 else 0
        val lowerRight : Int = if(x < maxX && y < maxY && paperRollStack[y + 1][x + 1] == '@') 1 else 0

        return upperLeft + upperMid + upperRight + midLeft + midRight + lowerLeft + lowerMid + lowerRight
    }
}
