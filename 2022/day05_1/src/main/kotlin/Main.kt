import java.io.File

fun main(args: Array<String>) {
    println("AOC 2022, Day 5, Part 1 starting!!!")

    var readingCrates = true
    val crateStacks = CrateStacks(args[1].toInt())

    File(args[0]).forEachLine {
        if(readingCrates) {
            readingCrates = processLineCrates(it, crateStacks)
        } else {
            processLineInstructions(it, crateStacks)
        }
    }

    crateStacks.printAllStacks()

    println("Reporting top of stacks: ${crateStacks.reportTopCrates()}")

    println("AOC 2022, Day 5, Part 1 completed!!")
}

data class CrateStacks constructor (val stackCount:Int) {
    val stacks:ArrayList<ArrayDeque<Char>> = ArrayList()

    init {
        for(i in 1 .. stackCount) {
            stacks.add(ArrayDeque())
        }
    }

    fun printAllStacks() {
        println("Current stack configuration:")

        for(i in 1 .. stackCount) {
            println("Stack $i: ${stacks[i - 1]}")
        }
    }

    fun move(moveCrates: Int, from: Int, to: Int) {
        println("Command: move $moveCrates from $from to $to")
        for(move in 1 .. moveCrates) {
            val theCrate = stacks[from - 1].removeFirst()
            stacks[to - 1].addFirst(theCrate)
        }
        printAllStacks()
        println("Moves complete")
    }

    fun reportTopCrates():String {
        var topOfStacks = ""
        for(stack in stacks) {
            topOfStacks += stack.first()
        }
        return topOfStacks
    }
}

fun processLineCrates(line: String, crateStacks: CrateStacks):Boolean {
    var chunkIndex = 0
    for(chunk in line.chunked(4)) {
        if(chunk.isNotBlank() && !chunk.contains(Regex("\\d"))) {
            //println("tlarsen,L47: $chunk with ${chunk[1]}")
            val theCrateStack = crateStacks.stacks[chunkIndex]
            theCrateStack.addLast(chunk[1])
        }
        chunkIndex++
    }

    return line.isNotBlank()
}

fun processLineInstructions(line: String, crateStacks: CrateStacks) {
    val split = line.split(' ')
    val moveCrates = split[1].toInt()
    val from = split[3].toInt()
    val to = split[5].toInt()
    crateStacks.move(moveCrates, from, to)
}