import java.io.File

fun main(args: Array<String>) {
    println("AOC 2023, Day 1, Part 2 starting!!!")

    var answer:Int = 0;

    File(args[0]).forEachLine {
        answer += parseLine(it)
    }
    println("The answer: $answer")

    println("AOC 2023, Day 1, Part 2 completed!!")
}

fun parseLine(line:String) : Int{
    val answerTens:Int = parseLeftToRight(line)
    val answerOnes:Int = parseRightToLeft(line)
    return (answerTens * 10) + answerOnes
}

fun parseLeftToRight(line:String):Int {
    if(line.first().isDigit()) {
        return line.first().digitToInt()
    }
    val number = getNumberLeft(line)
    if(number != 0) {
        return number
    }

    // nothing matches, recurse
    return parseLeftToRight(line.drop(1))
}

fun parseRightToLeft(line:String):Int {
    if(line.last().isDigit()) {
        return line.last().digitToInt()
    }
    val number = getNumberRight(line)
    if(number != 0) {
        return number
    }

    // Nothing matches, recurse
    return parseRightToLeft(line.dropLast(1))
}

val numberToDigit = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

fun getNumberLeft(line:String): Int {
    for(number in numberToDigit) {
        val safeEnd = number.key.length.coerceAtMost(line.length)
        val leftSubstring = line.substring(0, safeEnd)
        if(leftSubstring.compareTo(number.key) == 0) {
            return numberToDigit[leftSubstring]?:0
        }
    }
    return 0
}

fun getNumberRight(line:String):Int {
    for(number in numberToDigit) {
        val safeStart = (line.length - number.key.length).coerceAtLeast(0)
        val rightSubstring = line.substring(safeStart, line.length)
        if(rightSubstring.compareTo(number.key) == 0) {
            return numberToDigit[rightSubstring]?:0
        }
    }
    return 0
}