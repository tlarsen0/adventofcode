import java.io.File

fun main(args: Array<String>) {
    println("AOC 2021, Day 12.2 starting!!!!")

    val cavePaths = CavePaths()
    // args[0] should read /Users/tlarsen/src/adventofcode/2021/DAY-PART/src/main/resources
    File(args[0]).forEachLine {
        cavePaths.readLine(it)
    }

    println("There are ${cavePaths.caves.size} caves.")
    println("There are ${cavePaths.paths.size} paths between caves.")

    cavePaths.takeStep("start", ArrayList(), HashMap())

    println("Here are all of the discovered paths to the exit:")
    for(path in cavePaths.finishedPaths) {
        for(step in path) {
            print("$step ")
        }
        println(" ")
    }
    println("There are ${cavePaths.finishedPaths.size} number of paths in this cave system.")

    println("AOC 2021, Day 12.2 starting!!!!")
}

class CavePaths {
    val caves = ArrayList<String>()

    fun takeStep(caveName : String, currentPath: ArrayList<String>, lowerCaseCavesVisited: HashMap<String, Int>) : Boolean {
        // If end then stop
        if(caveName == "end") {
            // made it to the exit, finish off path!
            currentPath.add(caveName)
            finishedPaths.add(currentPath)
            return true
        }
        // Only see start once
        if((caveName == "start") && currentPath.contains(caveName))
            return false

        // Stepping into "caveName" but it is lowercase, only one can be visited twice.
        //if((caveName.lowercase() == caveName) && currentPath.contains(caveName))
        if(caveName.lowercase() == caveName) {
            if (!lowerCaseCavesVisited.keys.contains(caveName)) {
                lowerCaseCavesVisited[caveName] = 1
            } else if (!lowerCaseCavesVisited.values.contains(2) && (lowerCaseCavesVisited[caveName]!! == 1)) {
                lowerCaseCavesVisited[caveName] = 2
            } else {
                return false
            }
        }


        // remember where we are.
        currentPath.add(caveName)



        // take the next step to joining cave
        for(path in paths) {
            if(path.contains(caveName)) {
                val nextCave = path.getNextCave(caveName)
                val nextCurrentPath = ArrayList(currentPath)
                val nextLowerCaseCavesVisited = HashMap(lowerCaseCavesVisited)
                takeStep(nextCave, nextCurrentPath, nextLowerCaseCavesVisited)
            }
        }

        return false
    }

    val finishedPaths = ArrayList<ArrayList<String>>()

    // Left cave should always be smaller/lesser than right cave. This helps avoid duplicates.
    data class Path constructor(var leftCave: String, var rightCave: String){
        init {
            if(leftCave > rightCave) run {
                val temp = leftCave
                leftCave = rightCave
                rightCave = temp
            }
        }

        // The path "contains" the caveName if it is either leftCave or rightCave.
        fun contains(caveName:String) : Boolean{
            return (caveName == leftCave) || (caveName == rightCave)
        }

        fun getNextCave(caveName:String) : String {
            if(leftCave == caveName)
                return rightCave

            if(rightCave == caveName)
                return leftCave

            return ""
        }
    }

    val paths = ArrayList<Path>()

    fun readLine(line: String) {
        val split = line.split("-")

        // Add caves that are not already known
        for(cave in split) {
            if(!caves.contains(cave)) {
                caves.add(cave)
            }
        }

        // Add a path
        val path = Path(split[0], split[1])
        if(!paths.contains(path)) {
            paths.add(path)
        }
    }
}