import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 14.2 starting!!!!")

    val polymerBuilder = PolymerBuilder()
    // args[0] should read /Users/tlarsen/src/adventofcode/2021/DAY-PART/src/main/resources
    File(args[0]).forEachLine {
        if (polymerBuilder.polymerPairOfChar.isEmpty()) {
            polymerBuilder.addTemplate(it)
        } else if (it.isNotEmpty()) {
            polymerBuilder.addRule(it)
        }
    }

    println("Original polymer: ${polymerBuilder.polymerPairOfChar}")
    // build 10 times
    var polymer = ""
    for(i in 1..10) {
        polymer = polymerBuilder.build()
        //println("After step $i, the polymer is: $polymer")
        println("After step $i, the polymer is X long.")
    }
    println("Stats on the last polymer: ${polymer.length}")
    val chemCounter = HashMap<Char, Long>()
    for(ch in polymer) {
        if(!chemCounter.containsKey(ch)) {
            chemCounter[ch] = 0
        }
        chemCounter[ch] = chemCounter[ch]!! + 1L
    }

    println("Counts: $chemCounter")

    var mostCommonCount = 0L
    var leastCommonCount = 0L
    for(count in chemCounter.values) {
        if(mostCommonCount == 0L) {
            mostCommonCount = count
        } else {
            mostCommonCount = Math.max(count, mostCommonCount)
        }
        if(leastCommonCount == 0L) {
            leastCommonCount = count
        } else {
            leastCommonCount = Math.min(count, leastCommonCount)
        }
    }

    println("Most Common Count : $mostCommonCount")
    println("Least Common Count: $leastCommonCount")
    println("Difference        : ${mostCommonCount - leastCommonCount}")

    println("AOC 2021, Day 14.2 complete!!!!")
}

data class MyPair<L: Any, R: Any> constructor(val left:L, val right:R)

data class PairOfChar constructor(val left: Char, val right: Char)

class PolymerBuilder {
    var polymerPairOfChar = ArrayList<MyPair<Char, Char>>()
        private set
    var rules = HashMap<String, Char>()
        private set

    fun build() : String {
        val newPolymer = ArrayList<PairOfChar>()

        for(i in 0 until polymerPairOfChar.size - 1) {
            var copy = true
            for(rule in rules.keys) {
                if((polymer[i] == rule[0]) && (polymer[i + 1] == rule[1])) {
                    newPolymer += polymer[i]
                    newPolymer += rules[rule]!!

                    copy = false
                }
            }
            if(copy) {
                newPolymer += polymer[i]
            }
        }
        // the loop is always - 1 so copy forward the very last character
        newPolymer += polymer.last()

        polymer = newPolymer

        return String(newPolymer.toCharArray())
    }


    fun addTemplate(templ: String) {
        for(i in 0 until templ.length - 1) {
            polymerPairOfChar.add(PairOfChar(templ[i], templ[i + 1]))
        }
    }

    fun addRule(rule:String) {
        val splitPointer = rule.split("->") // split line left -> right
        rules[splitPointer[0].trim()] = splitPointer[1].trim()[0]
    }
}