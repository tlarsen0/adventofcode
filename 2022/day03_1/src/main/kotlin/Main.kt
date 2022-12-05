import java.io.File

data class RuckSack constructor(val contents:CharSequence)

class Compartment constructor(private val firstCompartment: RuckSack, private val secondCompartment: RuckSack) {
    fun getDuplicate():Char {
        val hashMap = HashMap<Char, Int>()
        for(c in firstCompartment.contents) {
            val count = hashMap[c] ?: 0
            hashMap[c] = count + 1
        }

        for(c in secondCompartment.contents) {
            if(hashMap.containsKey(c)) {
                return c
            }
        }

        return '*'
    }
}

fun main(args: Array<String>) {
    println("AOC 2022, Day 3, Part 1 starting!!!")

    val storageCompartment:ArrayList<Compartment> = ArrayList()

    File(args[0]).forEachLine {
        processLine(it, storageCompartment)
    }

    var totalPriority = 0
    for(compartment in storageCompartment) {
        val duplicate = compartment.getDuplicate()
        totalPriority += calculatePriority(duplicate)
    }

    println("Total priority = $totalPriority")

    println("AOC 2022, Day 3, Part 1 completed!!")
}

fun calculatePriority(priority:Char):Int {
    return if((priority >= 'a') && (priority <= 'z')) {
        1 + calculatePriorityHelper(priority, 'a')
    } else {
        27 + calculatePriorityHelper(priority, 'A')
    }
}

fun calculatePriorityHelper(priority: Char, step: Char):Int {
    if(priority > step) {
        return 1 + calculatePriorityHelper(priority, step.plus(1))
    }
    return 0
}

fun processLine(line:String, storage:ArrayList<Compartment>) {
    val first = line.subSequence(0, line.length / 2)
    val second = line.subSequence(line.length /2, line.length)

    storage.add(Compartment(RuckSack(first), RuckSack(second)))
}