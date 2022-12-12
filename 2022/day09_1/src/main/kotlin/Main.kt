import java.io.File

fun main(args: Array<String>) {
    println("AOC 2022, Day 9, Part 1 starting!!!")

    val rope = Rope(MyPosition(0,0), MyPosition(0,0))
    File(args[0]).forEachLine {
        val split = it.split(' ')
        rope.moveCommand(split[0], split[1].toInt())
    }

    println("Tail position: ${rope.countTailSteps()}")

    println("AOC 2022, Day 9, Part 1 completed!!")
}

data class MyPosition (var xPosition:Int, var yPosition:Int)

class Rope (private var headPosition:MyPosition, private var tailPosition:MyPosition) {
    private val tailRecord:HashMap<MyPosition, Int> = HashMap()

    init {
        tailRecord[MyPosition(0,0)] = 1
    }

    fun countTailSteps():Int {
        return tailRecord.size
    }

    fun moveCommand(moveDirection:String, steps:Int) {
        when(moveDirection) {
            "U" -> moveUp(steps)
            "D" -> moveDown(steps)
            "L" -> moveLeft(steps)
            "R" -> moveRight(steps)
        }
    }

    private fun moveUp(steps: Int) {
        for(step in 0 until steps) {
            headPosition.yPosition++
            if(!isTailTouchingHead()) {
                tailPosition.xPosition = headPosition.xPosition
                tailPosition.yPosition = headPosition.yPosition - 1

                val pos = MyPosition(tailPosition.xPosition, tailPosition.yPosition)
                val total = 1 + tailRecord.getOrDefault(pos, 0)
                tailRecord[pos] = total
            }
        }
    }

    private fun moveDown(steps: Int) {
        for(step in 0 until steps) {
            headPosition.yPosition--
            if(!isTailTouchingHead()) {
                tailPosition.xPosition = headPosition.xPosition
                tailPosition.yPosition = headPosition.yPosition + 1

                val pos = MyPosition(tailPosition.xPosition, tailPosition.yPosition)
                val total = 1 + tailRecord.getOrDefault(pos, 0)
                tailRecord[pos] = total
            }
        }
    }

    private fun moveLeft(steps: Int) {
        for(step in 0 until steps) {
            headPosition.xPosition--
            if(!isTailTouchingHead()) {
                tailPosition.xPosition = headPosition.xPosition + 1
                tailPosition.yPosition = headPosition.yPosition

                val pos = MyPosition(tailPosition.xPosition, tailPosition.yPosition)
                val total = 1 + tailRecord.getOrDefault(pos, 0)
                tailRecord[pos] = total
            }
        }
    }

    /*
..##..
...##.
.####.
....#.
s###..
 */

    private fun moveRight(steps: Int) {
        // 1. move head
        // 2. check if tail is still touching, if not move tail
        for(s in 0 until steps) {
            headPosition.xPosition++
            if(!isTailTouchingHead()) {
                tailPosition.xPosition = headPosition.xPosition - 1
                tailPosition.yPosition = headPosition.yPosition

                val pos = MyPosition(tailPosition.xPosition, tailPosition.yPosition)
                val total = 1 + tailRecord.getOrDefault(pos, 0)
                tailRecord[pos] = total
            }
        }
    }

    private fun isTailTouchingHead():Boolean {
        // head and tail is on top of each other
        if((headPosition.xPosition == tailPosition.xPosition) &&
            (headPosition.yPosition == tailPosition.yPosition)) {
            return true
        }
        if((headPosition.xPosition == tailPosition.xPosition) &&
            (headPosition.yPosition == (tailPosition.yPosition - 1))) {
            return true
        }
        if((headPosition.xPosition == tailPosition.xPosition) &&
            (headPosition.yPosition == (tailPosition.yPosition + 1))) {
            return true
        }
        if((headPosition.xPosition == (tailPosition.xPosition - 1)) &&
            (headPosition.yPosition == tailPosition.yPosition)) {
            return true
        }
        if((headPosition.xPosition == (tailPosition.xPosition + 1)) &&
            (headPosition.yPosition == tailPosition.yPosition)) {
            return true
        }

        // The four diagonal positions are also valid
        if((headPosition.xPosition == (tailPosition.xPosition - 1)) &&
            (headPosition.yPosition == (tailPosition.yPosition - 1))) {
            return true
        }
        if((headPosition.xPosition == (tailPosition.xPosition + 1)) &&
            (headPosition.yPosition == (tailPosition.yPosition + 1))) {
            return true
        }
        if((headPosition.xPosition == (tailPosition.xPosition - 1)) &&
            (headPosition.yPosition == (tailPosition.yPosition + 1))) {
            return true
        }
        if((headPosition.xPosition == (tailPosition.xPosition + 1)) &&
            (headPosition.yPosition == (tailPosition.yPosition - 1))) {
            return true
        }

        return false
    }
}