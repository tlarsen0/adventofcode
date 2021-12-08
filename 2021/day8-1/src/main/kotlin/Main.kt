import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 8.1 starting!!!!")

    var uniqueCount = 0
    val displayTest = ArrayList<SevenSegmentDisplay>()
    val displayPanel = ArrayList<SevenSegmentDisplay>()
    File(args[0]).forEachLine {
        val line = it.split("|")
        // left of the | : displaying 1 through 10
        val tenDigits = line[0].split(" ")
        for(digit in tenDigits) {
            displayTest.add(SevenSegmentDisplay(digit.trim()))
        }
        // right of the | : displaying 4 digit data
        val fourDigits = line[1].split(" ")
        for(digit in fourDigits) {
            if(digit.isEmpty())
                continue
            displayPanel.add(SevenSegmentDisplay(digit.trim()))
        }
    }

    for(display in displayPanel) {
        // Count only these
        // 1 = 2 segments
        // 4 = 4 segments
        // 7 = 3 segments
        // 8 = 7 segments
        if ((display.displaySegments.length == 2) || (display.displaySegments.length == 4) || (display.displaySegments.length == 3) || (display.displaySegments.length == 7))
            uniqueCount++
    }

    println("The unique count (displays with 2, 3, 4, or 7 segments enabled): $uniqueCount")

    println("AOC 2021, Day 8.1 complete!!!!")
}

class SevenSegmentDisplay constructor(val displaySegments : String) {

}