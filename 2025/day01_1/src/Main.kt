import java.io.File


fun main(args: Array<String>) {
    println("AOC 2025, Day 1, Part 1 starting!!!!")

    val safeDial = Dial()
    File(args[0]).forEachLine {
        safeDial.turnDial(it)
    }

    println("How many times Dial pointed to 0: ${safeDial.zeroCount}")

    println("AOC 2025, Day 1, Part 1 completed!!!")
}

class Dial (var pointsTo:Int = 50){
    var zeroCount:Int = 0
        private set

    fun turnDial(spinInst:String) {
        if(spinInst[0].compareTo('L') == 0) {
            // left instruction
            leftRotation(spinInst.substring(1).toInt())
        } else {
            // right instruction
            rightRotation(spinInst.substring(1).toInt())
        }
    }

    private fun leftRotation(leftTurn:Int) {
        pointsTo -= leftTurn
        while(pointsTo < 0) {
            pointsTo = 100 + pointsTo
        }
        if(pointsTo == 0) {
            zeroCount++
        }
    }

    private fun rightRotation(rightTurn:Int) {
        pointsTo += rightTurn
        while(pointsTo > 99) {
            pointsTo = pointsTo - 100
        }
        if(pointsTo == 0) {
            zeroCount++
        }
    }
}