import java.io.File
import kotlin.math.max

fun main(argv:Array<String>) {
    println("AOC 2025, Day 3, Part 1 starting!!!!")

    val batteryBank = BatteryBank(ArrayList())
    File(argv[0]).forEachLine {
        batteryBank.batteryBank.add(it)
    }

    var joltageTotal = 0L
    for(bank in batteryBank.batteryBank) {
        println("Joltage from bank $bank is ${calculateMaxJoltage(bank)}")
        joltageTotal += calculateMaxJoltage(bank)
    }

    println("Total Joltage output: $joltageTotal")

    println("AOC 2025, Day 3, Part 1 completed!!!")
}

fun calculateMaxJoltage(batteryBank: String) : Long {
    val twelveIndex = Array<Int>(12) { index -> index }

    return calculateMaxJoltage(batteryBank, twelveIndex, 11, Long.MIN_VALUE)
}

fun firstBatteryIndex(batteryIndexes: Array<Int>, modifyIndex : Int) : Int {
    // special case: index 0 is 0
    if(modifyIndex == 0) {
        return 0
    }

    // "first" is batteryIndexes[modifyIndex - 1] + 1, unless it is "packaged".
    val prevIndex = modifyIndex - 1
    if(batteryIndexes[prevIndex] != batteryIndexes[modifyIndex]) {
        return batteryIndexes[prevIndex] + 1
    }
    // ....else, batteryIndex are "packed", return the same index
    return batteryIndexes[modifyIndex]
}

fun lastBatteryIndex(batteryBank: String, batteryIndexes: Array<Int>, modifyIndex: Int) : Int {
    // special case: index 11 is last index = batteryBank.length - 1
    if(modifyIndex == 11) {
        return batteryBank.length - 1
    }

    // "last" is batteryIndexes[modifyIndex + 1] - 1, unless it is "packed"
    val nextIndex = modifyIndex + 1
    if(batteryIndexes[nextIndex] != batteryIndexes[modifyIndex]) {
        return batteryIndexes[nextIndex] - 1
    }
    // ....else, batteryIndex are "packed", return the same index
    return batteryIndexes[modifyIndex]
}

fun isBacktrackComplete(batteryBank: String, batteryIndexes: Array<Int>) : Boolean {
    if ((batteryIndexes[11] == batteryBank.length - 1) && (batteryIndexes[0] == batteryBank.length - 13)) {
        println("L62: backtrack complete!!!! ")
    }

    return (batteryIndexes[11] == batteryBank.length - 1) && (batteryIndexes[0] == batteryBank.length - 13)
}

fun calculateMaxJoltage(batteryBank: String, batteryIndexes: Array<Int>, modifyIndex : Int, maxJoltage: Long) : Long {
    if((modifyIndex < 0) || (modifyIndex > 11)) {
        // for sanity, check if modifying index is between 0 and 11, return minimum value
        //println("L31: modifyIndex ($modifyIndex) is OUT OF RANGE")
        return maxJoltage
    }

    // batteryIndex is at the maximum/all the way to the right. Return maxJoltage
    if(isBacktrackComplete(batteryBank, batteryIndexes)) {
        val currentMax = max(maxJoltage, stringToJoltage(batteryBank, batteryIndexes))
        //return max(currentMax, )
        return currentMax
    }

    // Start with the current Joltage of the battery bank.
    var currentMaxJoltage = stringToJoltage(batteryBank, batteryIndexes)

    // Rotate through all possibilities for this modifyIndex while recur call calculateMaxJoltage of the previous index
    val resetModifyIndex = firstBatteryIndex(batteryIndexes, modifyIndex)
    for(batteryIndex in firstBatteryIndex(batteryIndexes, modifyIndex)..lastBatteryIndex(batteryBank, batteryIndexes, modifyIndex)) {
        batteryIndexes[modifyIndex] = batteryIndex
        val nextJoltage = stringToJoltage(batteryBank, batteryIndexes)
        currentMaxJoltage = max(nextJoltage, currentMaxJoltage)
        currentMaxJoltage = max(calculateMaxJoltage(batteryBank, batteryIndexes, modifyIndex - 1, currentMaxJoltage), currentMaxJoltage)
    }
    //println("L90: out of loop! returning $currentMaxJoltage")
    // reset modifyIndex
    batteryIndexes[modifyIndex] = resetModifyIndex

    return currentMaxJoltage
}

fun stringToJoltage(batteryBank: String, batteryIndexes: Array<Int>) : Long {
    var strJoltage = ""
    for(index in 0..11) {
        strJoltage += "${batteryBank[batteryIndexes[index]]}"
    }
    //println("L107: strJoltage = $strJoltage")

    return strJoltage.toLong()
}

data class BatteryBank(val batteryBank: ArrayList<String>)
