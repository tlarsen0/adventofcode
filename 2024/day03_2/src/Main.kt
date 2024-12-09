import java.io.File

// important note: RegEx that handles the problem: mul\(\d{1,3},\d{1,3}\)
// mul\(\d{1,3},\d{1,3}\)|do\(\)|don\'t\(\)
fun main(args: Array<String>) {
    println("AOC 2024, Day 3, Part 2 starting!!!!")

    val theOperations = MulOperations()
    val theRegex = "mul\\(\\d{1,3},\\d{1,3}\\)|do\\(\\)|don\'t\\(\\)".toRegex()
    var enableMul = true
    File(args[0]).forEachLine {
        val theMatches = theRegex.findAll(it)
        for(match in theMatches) {
            if(match.value == "don't()") {
                enableMul = false
                continue
            }
            if(match.value == "do()") {
                enableMul = true
                continue
            }
            if(enableMul) {
                theOperations.dataList.add(match.value)
            }
        }
    }

    println("The answer: ${theOperations.results()}")

    println("AOC 2024, Day 3, Part 2 completed!!!")
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