import java.io.File

fun main(args: Array<String>) {
    println("AOC 2022, Day 1, Part 1 starting!!")
    println("    reading data from: ${args[0]}")

    val listOfElves:ArrayList<Elf> = ArrayList()
    listOfElves.add(Elf())

    File(args[0]).forEachLine {
        processLine(it, listOfElves)
    }

    println("Count of elves: ${listOfElves.size}")
    val mostCaloriesCarried = listOfElves.maxOf { it.calories }
    println("Maximum carried by an elf: $mostCaloriesCarried")
}

class Elf {
    var calories:Int = 0
        private set

    fun addFood(newFood:Int) {
        calories += newFood
    }
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