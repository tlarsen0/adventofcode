import java.io.File

fun main(argv: Array<String>) {
    println("AOC 2025, Day 5, Part 1 starting!!!!")
    println("Details of problem are here: https://adventofcode.com/2025/day/5")

    val allFreshRanges = ArrayList<IngredientRange>()
    val allIngredients = ArrayList<Ingredient>()

    var emptyLine = false
    File(argv[0]).forEachLine {
        if(!emptyLine && it.isEmpty()) {
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

    var freshCount = 0
    allIngredients.forEach {
        run ingredients@{
            allFreshRanges.forEach { freshRange ->
                run ranges@{
                    if (freshRange.isFresh(it.ingredientId)) {
                        println("Ingredient ID ${it.ingredientId} is fresh!")
                        freshCount++
                        return@ingredients // stop searching for "fresh" if found once
                    }
                }
            }
        }
    }

    println("Fresh ingredient count: $freshCount")

    println("AOC 2025, Day 5, Part 1 completed!!!")
}

data class IngredientRange(val theStart: Long, val theEnd: Long) {
    fun isFresh(ingredientId: Long) : Boolean {
        return (theStart <= ingredientId) && (ingredientId <= theEnd)
    }
}

data class Ingredient(val ingredientId: Long)
