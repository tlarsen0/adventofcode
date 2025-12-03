import java.io.File
import kotlin.math.max

fun main(argv:Array<String>) {
    println("AOC 2025, Day 3, Part 1 starting!!!!")

    val batteryBank = BatteryBank(ArrayList<String>())
    File(argv[0]).forEachLine {
        batteryBank.batteryBank.add(it)
    }

    var joltageTotal = 0
    for(bank in batteryBank.batteryBank) {
        println("Joltage from bank $bank is ${calculateMaxJoltage(bank)}")
        joltageTotal += calculateMaxJoltage(bank)
    }

    println("Total Joltage output: $joltageTotal")

    println("AOC 2025, Day 3, Part 1 completed!!!")
}

fun calculateMaxJoltage(batteryBank:String) : Int {
    var maxJoltage = Int.MIN_VALUE

    for(leftIndex in 0 until batteryBank.length - 1) {
        for(rightIndex in leftIndex + 1 until batteryBank.length) {
            val twoDigitVal = "${batteryBank[leftIndex]}${batteryBank[rightIndex]}".toInt()
            maxJoltage = maxJoltage.coerceAtLeast(twoDigitVal)
        }
    }

    return maxJoltage
}

data class BatteryBank(val batteryBank: ArrayList<String>)
