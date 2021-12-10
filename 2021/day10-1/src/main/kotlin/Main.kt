import java.io.File
import java.util.*
import kotlin.collections.HashMap

fun main(args: Array<String>) {
    println("AOC 2021, Day 10.1 starting!!!!")

    var mismatchScore = 0
    val navSystemParser = NavSystemParser()
    File(args[0]).forEachLine {
        val mismatchChar = navSystemParser.readLine(it)
        // If 0 was returned, everything is okay and continue
        if(mismatchChar != '0') {
            mismatchScore += Score.getScore(mismatchChar)
        }
    }

    println("Syntax Error Score: $mismatchScore")

    println("AOC 2021, Day 10.1 complete!!!!")
}

// This should be static...
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

class NavSystemParser {
    private val stack = Stack<Char>()
    private val charMap = HashMap<Char, Char>()

    init {
        charMap['('] = ')'
        charMap['['] = ']'
        charMap['{'] = '}'
        charMap['<'] = '>'
    }

    fun readLine(line : String) : Char {
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
}