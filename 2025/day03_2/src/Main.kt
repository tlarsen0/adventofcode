import java.io.File

fun main(argv:Array<String>) {
    println("AOC 2025, Day 3, Part 2 starting!!!!")

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

    println("AOC 2025, Day 3, Part 2 completed!!!")
}

fun calculateMaxJoltage(batteryBank: String) : Long {
    val batteryIndexes = Array<Int>(12) { index -> index }

    return calculateMaxJoltage(batteryBank, batteryIndexes)
}

fun calculateMaxJoltage(batteryBank : String, batteryIndexes : Array<Int>) : Long {
    // from 0 to 11 as currIndex, from currentIndex to the "end", find "the largest but left most number".
    for(currIndex in 0..11) {
        val firstBatteryIndex = firstBatteryIndex(batteryIndexes, currIndex)
        val lastBatteryIndex = lastBatteryIndex(batteryBank, currIndex)
        var maxIndex = 0
        var maxJoltage = 0
        for(modifyIndex in firstBatteryIndex..lastBatteryIndex) {
            if(maxJoltage < batteryBank[modifyIndex].digitToInt()) {
                maxJoltage = batteryBank[modifyIndex].digitToInt()
                maxIndex = modifyIndex
            }
        }
        batteryIndexes[currIndex] = maxIndex
    }
    return stringToJoltage(batteryBank, batteryIndexes)
}

// battery bank: 234234234234278
// index 0: scan from 0 to 3   : 0 = 15 (batteryBank.length) - (15 + 0), 3 = 15 - 12 - 0
// index 1: scan from 1 to 4   : 1 = 15 (batteryBank.length) - (15 + 1), 4 = 15 - 12 - 1
// index 2: scan from 2 to 5   : 2 = 15 - (15 + 2), 5 = 15 - 12 - 2
// index 3: scan from 3 to 6   : 3 = 15 - (15 + 3), 6 = 15 - 12 - 3
// index 4: scan from 4 to 7   : 4 = 15 - (15 + 4), 7 = 15 - 12 - 4
// ....
// index 11: scan from 11 to 14 : 11 = 11,  14 = 15 - 12 - 11

fun firstBatteryIndex(batteryIndexes: Array<Int>, modifyIndex : Int) : Int {
    // special case: index 0 is 0
    if(modifyIndex == 0) {
        return 0
    }

    // For any modifyIndex, the "first" is batteryIndexes[modifyIndex - 1] + 1, unless it is "packaged".
    return batteryIndexes[modifyIndex - 1] + 1
}

fun lastBatteryIndex(batteryBank: String, modifyIndex: Int) : Int {
    // special case: index 11 is last index = batteryBank.length - 1
    if(modifyIndex == 11) {
        return batteryBank.length - 1
    }

    return (batteryBank.length - 12) + modifyIndex
}

fun stringToJoltage(batteryBank: String, batteryIndexes: Array<Int>) : Long {
    var strJoltage = ""
    for(index in 0..11) {
        strJoltage += "${batteryBank[batteryIndexes[index]]}"
    }

    return strJoltage.toLong()
}

data class BatteryBank(val batteryBank: ArrayList<String>)
