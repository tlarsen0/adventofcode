import java.io.File


fun main(args: Array<String>) {
    println("AOC 2025, Day 1, Part 2 starting!!!!")

    val safeDial = Dial()
    File(args[0]).forEachLine {
        safeDial.turnDial(it)
    }

    println("How many times Dial passed 0: ${safeDial.zeroCount}")

    println("AOC 2025, Day 1, Part 2 completed!!!")
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
        // model turning the dial with a loop
        for(i in 1..leftTurn) {
            pointsTo--
            if(pointsTo == 0) {
                zeroCount++
            }
            if(pointsTo < 0) {
                pointsTo = 99
            }
        }
    }

    private fun rightRotation(rightTurn:Int) {
        // model turning the dial with a loop
        for(i in 1..rightTurn) {
            pointsTo++
            if(pointsTo > 99) {
                pointsTo = 0
                zeroCount++
            }
        }
    }
}