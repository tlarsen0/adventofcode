import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 2.1 starting!!!!")

    val mySub = TheSub()
    File(args[0]).forEachLine {
        processLine(it, mySub)
    }

    mySub.answer()

    println("AOC 2021, Day 2.1 complete!!!!")
}

fun processLine(line : String, sub : TheSub) {
    val theSplitString = line.split("\\s+".toRegex())
    val direction = theSplitString[0]
    val steps = theSplitString[1].toInt()
    //println("tlarsen,L19: direction = $direction, steps = $steps")
    sub.move(direction, steps)
}

class TheSub {
    var depth : Int = 0
    var horizontal : Int = 0

    fun move(direction : String, steps : Int) {
        if (direction.compareTo("forward") == 0) {
            horizontal += steps
        }
        if (direction.compareTo("down") == 0) {
            depth += steps
        }
        if (direction.compareTo("up") == 0) {
            depth -= steps
        }
    }

    fun answer() {
        println("latest depth      = $depth")
        println("latest horizontal = $horizontal")
        println("my answer = " + (depth * horizontal))
    }
}



