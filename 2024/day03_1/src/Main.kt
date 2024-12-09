import java.io.File

// important note: RegEx that handles the problem: mul\(\d{1,3},\d{1,3}\)
fun main(args: Array<String>) {
    println("AOC 2024, Day 3, Part 1 starting!!!!")

    val theOperations = MulOperations()
    val theRegex = "mul\\(\\d{1,3},\\d{1,3}\\)".toRegex()
    File(args[0]).forEachLine {
        val theMatches = theRegex.findAll(it)
        for(match in theMatches) {
            theOperations.dataList.add(match.value)
        }
    }

    println("The answer: ${theOperations.results()}")

    println("AOC 2024, Day 3, Part 1 completed!!!")
}

class MulOperations {
    val dataList = ArrayList<String>()

    fun results():Long {
        var theResult = 0L
        val regex = "(\\d{1,3})".toRegex()
        for(d in dataList) {
            val theMatches = regex.findAll(d)
            theResult += (theMatches.first().value.toLong() * theMatches.last().value.toLong())
        }
        return theResult
    }
}