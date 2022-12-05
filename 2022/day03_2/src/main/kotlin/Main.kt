import java.io.File

data class RuckSack constructor(val contents:CharSequence)

class Compartment constructor(val firstCompartment: RuckSack, val secondCompartment: RuckSack)

data class Elf constructor(val compartment: Compartment)

class ThreeElves {
    constructor(firstElf: Elf) {
        elves.add(firstElf)
        elves.add(Elf(Compartment(RuckSack("***"), RuckSack("***"))))
        elves.add(Elf(Compartment(RuckSack("***"), RuckSack("***"))))
    }
    private val elves = ArrayList<Elf>()

    private fun getFirstElf():Elf {
        return elves[0]
    }

    var secondElfSet:Boolean = false

    fun setSecondElf(elf:Elf) {
        elves[1] = elf
        secondElfSet = true
    }
    private fun getSecondElf():Elf {
        return elves[1]
    }

    var thirdElfSet:Boolean = false

    fun setThirdElf(elf:Elf) {
        elves[2] = elf
        thirdElfSet = true
    }
    private fun getThirdElf():Elf {
        return elves[2]
    }

    fun areAllElvesSet():Boolean {
        return secondElfSet && thirdElfSet
    }

    fun findDuplicate():Char {
        val hashSet = HashSet<Char>()
        for(c in getFirstElf().compartment.firstCompartment.contents) {
            hashSet.add(c)
        }
        for(c in getFirstElf().compartment.secondCompartment.contents) {
            hashSet.add(c)
        }

        val commonFirstSecond = HashSet<Char>()
        for(c in getSecondElf().compartment.firstCompartment.contents) {
            if(hashSet.contains(c)) {
                commonFirstSecond.add(c)
            }
        }
        for(c in getSecondElf().compartment.secondCompartment.contents) {
            if(hashSet.contains(c)) {
                commonFirstSecond.add(c)
            }
        }
        val commonFirstThird = HashSet<Char>()
        for(c in getThirdElf().compartment.firstCompartment.contents) {
            if(hashSet.contains(c)) {
                commonFirstThird.add(c)
            }
        }
        for(c in getThirdElf().compartment.secondCompartment.contents) {
            if(hashSet.contains(c)) {
                commonFirstThird.add(c)
            }
        }

        for(c in commonFirstSecond) {
            if(commonFirstThird.contains(c)) {
                return c
            }
        }

        return '*'
    }
}

fun main(args: Array<String>) {
    println("AOC 2022, Day 3, Part 2 starting!!!")

    val groupOfThreeElves = ArrayList<ThreeElves>()

    File(args[0]).forEachLine {
        processLine(it, groupOfThreeElves)
    }

    var totalPriority = 0
    for(groupOfThree in groupOfThreeElves) {
//        groupOfThree.getFirstElf().compartment.showBags()
//        groupOfThree.getSecondElf().compartment.showBags()
//        groupOfThree.getThirdElf().compartment.showBags()
        val duplicate = groupOfThree.findDuplicate()
        totalPriority += calculatePriority(duplicate)
    }

    println("Total priority = $totalPriority")

    println("AOC 2022, Day 3, Part 2 completed!!")
}

fun processLine(line:String, groups:ArrayList<ThreeElves>) {
    val first = line.subSequence(0, line.length / 2)
    val second = line.subSequence(line.length /2, line.length)
    val compartment = Compartment(RuckSack(first), RuckSack(second))

    if(groups.isEmpty()) {
        groups.add(ThreeElves(Elf(compartment)))
        return
    }

    val lastGroup = groups.last()

    if(lastGroup.areAllElvesSet()) {
        groups.add(ThreeElves(Elf(compartment)))
        return
    }

    if (!lastGroup.secondElfSet) {
        lastGroup.setSecondElf(Elf(compartment))
        return
    }
    if (!lastGroup.thirdElfSet) {
        lastGroup.setThirdElf(Elf(compartment))
        return
    }
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