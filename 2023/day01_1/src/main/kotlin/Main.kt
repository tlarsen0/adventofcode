import java.io.File

fun main(args: Array<String>) {
    println("AOC 2023, Day 1, Part 1 starting!!!")

    var answer:Int = 0;

    File(args[0]).forEachLine {
        answer += parseLine(it)
    }
    println("The answer: $answer")

    println("AOC 2023, Day 1, Part 1 completed!!")
}

fun parseLine(line:String) : Int{
    val answerTens:Int = parseLeftToRight(line)
    val answerOnes:Int = parseRightToLeft(line)
    return (answerTens * 10) + answerOnes
}

fun parseLeftToRight(line:String):Int {
    if(!line.first().isDigit()) {
        return parseLeftToRight(line.drop(1))
    }
    return line.first().digitToInt()
}

fun parseRightToLeft(line:String):Int {
    if(!line.last().isDigit()) {
        return parseRightToLeft(line.dropLast(1))
    }
    return line.last().digitToInt()
}