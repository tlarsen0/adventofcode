import java.io.File
import kotlin.math.abs

fun main(args: Array<String>) {
    println("AOC 2024, Day 1, Part 1 starting!!!!")

    val dualList = DualList()

    File(args[0]).forEachLine {
        val theSplit = it.split("   ")
        dualList.leftList.add(theSplit[0].toLong())
        dualList.rightList.add(theSplit[1].toLong())
    }

    dualList.orderLists()
    val answer = dualList.calculateTotalDistance()

    println("The total distance between left/right lists is: $answer")

    println("AOC 2024, Day 1, Part 1 completed!!!")
}

class DualList {
    val leftList = ArrayList<Long>()
    val rightList = ArrayList<Long>()

    fun orderLists() {
        leftList.sort()
        rightList.sort()
    }

    fun calculateTotalDistance():Long {
        var totalDistance = 0L
        for(index in 0..< leftList.size) {
            totalDistance += abs(leftList[index] - rightList[index])
        }

        return totalDistance
    }
}