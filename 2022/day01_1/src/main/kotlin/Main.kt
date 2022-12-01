import java.io.File

val listOfElves:ArrayList<Elf> = ArrayList()

fun main(args: Array<String>) {
    println("AOC 2022, Day 1, Part 1 starting!!")

    listOfElves.add(Elf())

    File(args[0]).forEachLine {
        processLine(it)
    }

    println("Count of elves: ${listOfElves.size}")
    val mostCaloriesCarried = listOfElves.maxOfOrNull { it.calories }
    println("Maximum carried by an elf: $mostCaloriesCarried")
}

class Elf {
    var calories:Int = 0
        private set

    fun addFood(newFood:Int) {
        calories += newFood
    }
}

fun processLine(line:String) {
    // read each line, blank links separate elves
    if(line.isBlank()) {
        // blank line, new elf
        listOfElves.add(Elf())
    } else {
        // add more food to last elf in line
        val elf = listOfElves.last()
        elf.addFood(line.toInt())
    }
}