import java.io.File
import java.util.*
import kotlin.collections.HashMap

fun main(args: Array<String>) {
    println("AOC 2021, Day 10.2 starting!!!!")

    var mismatchScore = 0
    val navSystemParser = NavSystemParser()
    File(args[0]).forEachLine {
        val mismatchChar = navSystemParser.checkForMismatch(it)
        // If 0 was returned, everything is okay and continue
        if(mismatchChar != '0') {
            // corrupted lines
            mismatchScore += Score.getScore(mismatchChar)
        } else {
            // incomplete lines
            navSystemParser.completeMissing(it)
        }
    }

    println("Syntax Error Score: $mismatchScore")

    println("Missing/Autocomplete Score: " + navSystemParser.getMissingScore())

    println("AOC 2021, Day 10.2 complete!!!!")
}

// This should be static?
class Score {
    companion object {
        private val scores = HashMap<Char, Int>()

        init {
            scores[')'] = 3
            scores[']'] = 57
            scores['}'] = 1197
            scores['>'] = 25137
        }

        fun getScore(c: Char): Int {
            return scores[c]!!
        }
    }
}

class ScoreMissing {
    companion object {
        private val scores = HashMap<Char, Int>()

        init {
            scores[')'] = 1
            scores[']'] = 2
            scores['}'] = 3
            scores['>'] = 4
        }

        fun getScore(c: Char): Int {
            return scores[c]!!
        }
    }
}

class NavSystemParser {
    private val charMap = HashMap<Char, Char>()

    init {
        charMap['('] = ')'
        charMap['['] = ']'
        charMap['{'] = '}'
        charMap['<'] = '>'
    }

    fun checkForMismatch(line : String) : Char {
        val stack = Stack<Char>()
        for(c in line) {
            // c is a new open character, push it on the stack.
            if (charMap.keys.contains(c)) {
                stack.push(c)
            } else {
                // c is some other character, pop the stack and check if they are matching.
                val prevC = stack.pop()
                if(charMap[prevC] != c) {
                    return c
                }
            }
        }
        return '0'
    }

    private val missingScores = ArrayList<Long>()

    fun completeMissing(line : String) {
        val stack = Stack<Char>()
        for(c in line) {
            if(charMap.keys.contains(c)) {
                stack.push(c)
            } else {
                val prevC = stack.pop()
                if(charMap[prevC] != c) {
                    throw Exception("hey this shouldn't happen!")
                }
            }
        }

        var missing = ""
        while(stack.isNotEmpty()) {
            missing += charMap[stack.pop()]
        }
        println("Missing segment line + missing segment: $line - $missing")
        var score = 0L
        for(c in missing) {
            score = score * 5 + ScoreMissing.getScore(c)
        }
        println("Missing Score: $score")
        missingScores.add(score)
    }

    fun getMissingScore() : Long {
        val sortedScores = missingScores.sorted()
        val index = sortedScores.size / 2
        println("tlarsen,L122: sortedScores.size = ${sortedScores.size}, missingScores.size = ${missingScores.size}, half = " + (missingScores.size / 2))
        return sortedScores[index]
    }
}
