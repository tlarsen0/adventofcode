import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 13.1 starting!!!!")

    val transparentPage = TransparentPage()
    // args[0] should read /Users/tlarsen/src/adventofcode/2021/DAY-PART/src/main/resources
    var whiteSpace = false
    File(args[0]).forEachLine {
        // ready pairs of numbers till a white space
        whiteSpace = whiteSpace || it.isEmpty()
        if(it.isNotEmpty()) { // only read lines with data, skip the one that is blank
            if (!whiteSpace) {
                val split = it.split(",")
                transparentPage.addDot(split[0].toInt(), split[1].toInt())
            } else {
                val xOrY = it[11]
                val foldOn = it.substring(13).toInt()
                transparentPage.addFold(xOrY, foldOn)
            }
        }
    }

    //transparentPage.showPage()
    println("Dots displayed: ${transparentPage.countDots()}")

    println("Folding step 0")
    val newPage = transparentPage.executeFold(0)
    //newPage.showPage()
    println("Dots displayed: ${newPage.countDots()}")

    println("AOC 2021, Day 13.1 complete!!!!")
}

data class MyCoord constructor(val x: Int, val y: Int)

data class Fold constructor(val xOrY: Char, val foldOn: Int)

class TransparentPage {
    private val dots = ArrayList<MyCoord>()
    var maxX = 0
    var maxY = 0
    private val foldInstructions = ArrayList<Fold>()

    fun addDot(x : Int, y : Int) {
        val newDot = MyCoord(x, y)
        // if this dot already exists, skip it.
        if(dots.contains(newDot))
            return
        dots.add(MyCoord(x, y))
        maxX = x.coerceAtLeast(maxX)
        maxY = y.coerceAtLeast(maxY)
    }
    fun addDot(dot:MyCoord) {
        // if this dot already exists, skip it.
        if(dots.contains(dot))
            return
        dots.add(dot)
        maxX = dot.x.coerceAtLeast(maxX)
        maxY = dot.y.coerceAtLeast(maxY)
    }

    fun addFold(xOrY: Char, foldOn: Int) {
        foldInstructions.add(Fold(xOrY, foldOn))
    }

    fun showPage() {
        for(y in 0 .. maxY) {
            for(x in 0 .. maxX) {
                if(dots.contains(MyCoord(x, y))) {
                    print("#")
                } else {
                    print(".")
                }
            }
            println(" ")
        }
    }

    fun countDots() : Int {
        return dots.size
    }

    fun executeFold(step:Int) : TransparentPage {
        val theFold = foldInstructions[step]
        val foldedPage = TransparentPage()

        if(theFold.xOrY == 'x') {
            foldedPage.maxX = theFold.foldOn
        } else if(theFold.xOrY == 'y') {
            foldedPage.maxY = theFold.foldOn
        }

        for(dot in dots) {
            if(theFold.xOrY == 'x') {
                // the dot is to the left of the fold line, just copy it over
                if(dot.x < theFold.foldOn) {
                    foldedPage.addDot(dot)
                } else {
                    // transpose dot in x
                    val newX = theFold.foldOn - (dot.x - theFold.foldOn)
                    foldedPage.addDot(newX, dot.y)
                }
            } else if(theFold.xOrY == 'y') {
                // dot is above the fold line, just copy it over
                if(dot.y < theFold.foldOn) {
                    foldedPage.addDot(dot)
                } else {
                    // transpose dot in y
                    val newY = theFold.foldOn - (dot.y - theFold.foldOn)
                    foldedPage.addDot(dot.x, newY)
                }
            }
        }

        return foldedPage
    }
}