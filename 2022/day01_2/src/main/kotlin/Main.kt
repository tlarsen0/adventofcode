import java.io.File

class Elf {
    var calories:Int = 0
        private set

    fun addFood(newFood:Int) {
        calories += newFood
    }
}

fun main(args: Array<String>) {
    println("AOC 2022, Day 1, Part 2 starting!!")

    val listOfElves:ArrayList<Elf> = ArrayList()
    listOfElves.add(Elf())

    File(args[0]).forEachLine {
        processLine(it, listOfElves)
    }

    val sortedElves = listOfElves.sortedByDescending { it.calories }

    println("The top 3 Elves:")

    var combinedTotal = 0
    for(i in 0 .. 2) {
        println("${i + 1} carries ${sortedElves[i].calories}")
        combinedTotal += sortedElves[i].calories
    }

    println("For a combined total: $combinedTotal")

    println("AOC 2022, Day 1, Part 2 completed.")
}

fun processLine(line:String, elves:ArrayList<Elf>) {
    // read each line, blank links separate elves
    if(line.isBlank()) {
        // blank line, new elf
        elves.add(Elf())
    } else {
        // add more food to last elf in line
        val elf = elves.last()
        elf.addFood(line.toInt())
    }
}