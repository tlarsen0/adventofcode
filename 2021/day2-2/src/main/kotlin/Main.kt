import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 2.2 starting!!!!")

    val mySub = TheSub()
    File(args[0]).forEachLine {
        mySub.processLine(it)
    }

    mySub.answer()

    println("AOC 2021, Day 2.2 complete!!!!")
}

class TheSub {
    private var aim : Long = 0
    private var depth : Long = 0
    private var horizontal : Long = 0

    fun move(direction : String, steps : Long) {
        if (direction.compareTo("forward") == 0) {
            horizontal += steps
            depth += aim * steps
        }
        if (direction.compareTo("down") == 0) {
            aim += steps
        }
        if (direction.compareTo("up") == 0) {
            aim -= steps
        }
    }

    fun processLine(line : String) {
        val theSplitString = line.split("\\s+".toRegex())
        val direction = theSplitString[0]
        val steps = theSplitString[1].toLong()

        move(direction, steps)
    }


    fun answer() {
        println("latest aim        = $aim")
        println("latest depth      = $depth")
        println("latest horizontal = $horizontal")
        println("my answer = " + (depth * horizontal))
    }
}
