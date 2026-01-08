import java.io.File

fun main(argv: Array<String>) {
    println("AOC 2025, Day 5, Part 2 starting!!!!")
    println("Details of problem are here: https://adventofcode.com/2025/day/5#part2")

    val allFreshRanges = ArrayList<IngredientRange>()
    val allIngredients = ArrayList<Ingredient>()

    var emptyLine = false
    File(argv[0]).forEachLine {
        if (!emptyLine && it.isEmpty()) {
            emptyLine = true
        } else {
            if (!emptyLine) {
                val split = it.split('-')
                allFreshRanges.add(IngredientRange(split[0].toLong(), split[1].toLong()))
            } else {
                allIngredients.add(Ingredient(it.toLong()))
            }
        }
    }

    val freshRanges = mergeRanges(allFreshRanges)
    val freshIngredientCount = freshRanges.sumOf { it.theEnd - it.theStart + 1 }

    println("Fresh ingredient count: $freshIngredientCount")

    println("AOC 2025, Day 5, Part 2 completed!!!")
}

/**
 * A "standard" way to merge a List of start/end ranges.
 * example: (4, 8), (1, 5) -> (1, 8)
 */
fun mergeRanges(ranges: ArrayList<IngredientRange>) : ArrayList<IngredientRange> {
    // ignore empty case?
    if(ranges.isEmpty()) {
        return ArrayList()
    }

    val sortedRanges = ranges.sortedBy { it.theStart }
    val mergedList = ArrayList<IngredientRange>()

    mergedList.add(sortedRanges[0])

    for(i in 1 until sortedRanges.size) {
        val currentElement = sortedRanges[i]
        val lastMergedElement = mergedList.last()

        // if the start of current is before the end of the last element, there is overlapping start/end ranges
        if(currentElement.theStart <= lastMergedElement.theEnd) {
            val mergedTheEnd = maxOf(lastMergedElement.theEnd, currentElement.theEnd)
            mergedList[mergedList.lastIndex] = lastMergedElement.copy(theEnd = mergedTheEnd)
        } else {
            // no overlap, add it to list
            mergedList.add(currentElement)
        }
    }

    return mergedList
}

data class IngredientRange(val theStart: Long, val theEnd: Long)

data class Ingredient(val ingredientId: Long)
