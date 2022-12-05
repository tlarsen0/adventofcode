import java.io.File

fun main(args: Array<String>) {
    println("AOC 2022, Day 4, Part 2 starting!!!")

    val allElfTeams = ArrayList<ElfTeam>()

    File(args[0]).forEachLine {
        processLine(it, allElfTeams)
    }

    var overlapCount = 0
    for(elfTeam in allElfTeams) {
        if(elfTeam.hasOverlap()) {
            overlapCount++
        }
    }
    println("Overlap count is: $overlapCount")

    println("AOC 2022, Day 4, Part 2 completed!!")
}

fun processLine(line:String, allElves:ArrayList<ElfTeam>) {
    val splitByElf = line.split(',')
    val firstSpan = splitByElf[0].split('-')
    val secondSpan = splitByElf[1].split('-')

    val elfTeam = ElfTeam(
        ElfAssignment(firstSpan[0].toInt(), firstSpan[1].toInt()),
        ElfAssignment(secondSpan[0].toInt(), secondSpan[1].toInt())
    )
    allElves.add(elfTeam)
}

class ElfTeam constructor(private var firstElfAssignment:ElfAssignment, private var secondElfAssignment:ElfAssignment){

    fun hasOverlap():Boolean {
        //println("tlarsen,L38: firstElfAssignment = $firstElfAssignment, secondElfAssignment = $secondElfAssignment")
        if((firstElfAssignment.startID <= secondElfAssignment.startID) && (firstElfAssignment.endID >= secondElfAssignment.startID)) {
            return true
        }
        if((firstElfAssignment.startID <= secondElfAssignment.endID) && (firstElfAssignment.startID >= secondElfAssignment.endID)) {
            return true
        }

        if((secondElfAssignment.startID <= firstElfAssignment.startID) && (secondElfAssignment.endID >= firstElfAssignment.startID)) {
            return true
        }
        if((secondElfAssignment.startID <= firstElfAssignment.endID) && (secondElfAssignment.endID >= firstElfAssignment.endID)) {
            return true
        }

        return false
    }
}

data class ElfAssignment constructor(val startID:Int, val endID:Int)
