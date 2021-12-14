import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 14.1 starting!!!!")

    val polymerBuilder = PolymerBuilder()
    // args[0] should read /Users/tlarsen/src/adventofcode/2021/DAY-PART/src/main/resources
    File(args[0]).forEachLine {
        if (polymerBuilder.polymer.isEmpty()) {
            polymerBuilder.addTemplate(it)
        } else if (it.isNotEmpty()) {
            polymerBuilder.addRule(it)
        }
    }

    println("Original polymer: ${polymerBuilder.polymer}")
    // build 10 times
    var polymer = ""
    for(i in 1..10) {
        polymer = polymerBuilder.build()
        println("After step $i, the polymer is: $polymer")
    }
    println("Stats on the last polymer: ${polymer.length}")
    val chemCounter = HashMap<Char, Int>()
    for(ch in polymer) {
        if(!chemCounter.containsKey(ch)) {
            chemCounter[ch] = 0
        }
        chemCounter[ch] = chemCounter[ch]!! + 1
    }

    println("Counts: $chemCounter")

    var mostCommonCount = 0
    var leastCommonCount = 0
    for(count in chemCounter.values) {
        if(mostCommonCount == 0) {
            mostCommonCount = count
        } else {
            mostCommonCount = Math.max(count, mostCommonCount)
        }
        if(leastCommonCount == 0) {
            leastCommonCount = count
        } else {
            leastCommonCount = Math.min(count, leastCommonCount)
        }
    }

    println("Most Common Count : $mostCommonCount")
    println("Least Common Count: $leastCommonCount")
    println("Difference        : ${mostCommonCount - leastCommonCount}")

    println("AOC 2021, Day 14.1 complete!!!!")
}


class PolymerBuilder {
    var polymer = ArrayList<Char>()
        private set
    var rules = HashMap<String, Char>()
        private set

    fun build() : String {
        val newPolymer = ArrayList<Char>()

        for(i in 0 until polymer.size - 1) {
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
        for(ch in templ) {
            polymer.add(ch)
        }
    }

    fun addRule(rule:String) {
        val splitPointer = rule.split("->") // split line left -> right
        rules[splitPointer[0].trim()] = splitPointer[1].trim()[0]
    }
}