import java.io.File

fun main(args: Array<String>) {
    println("AOC 2022, Day 4, Part 1 starting!!!")

    val allElfTeams = ArrayList<ElfTeam>()

    File(args[0]).forEachLine {
        processLine(it, allElfTeams)
    }

    val overlapCount = allElfTeams.stream().filter { it.hasOverlap() }.count()

    println("The overlap count is: $overlapCount")

    println("AOC 2022, Day 4, Part 1 completed!!")
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
        if((firstElfAssignment.startID <= secondElfAssignment.startID) && (firstElfAssignment.endID >= secondElfAssignment.endID)) {
            return true
        }

        if((secondElfAssignment.startID <= firstElfAssignment.startID) && (secondElfAssignment.endID >= firstElfAssignment.endID)){
            return true
        }

        return false
    }
}

data class ElfAssignment constructor(val startID:Int, val endID:Int)