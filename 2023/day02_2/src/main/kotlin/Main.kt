import java.io.File

fun main(args: Array<String>) {
    println("AOC 2023, Day 2, Part 2 starting!!!")

    val games:ArrayList<Game> = ArrayList()
    File(args[0]).forEachLine {
        games.add(parseGameLine(it))
    }

    var answer = 0
    games.stream().forEach { answer += it.getFewestRedCount() * it.getFewestGreenCount() * it.getFewestBlueCount() }

    println("Answer: $answer")

    println("AOC 2023, Day 2, Part 2 completed!!")
}


fun parseGameLine(line:String):Game {
    val gameNumber = line.split(":")[0].drop(5).toInt()
    val newGame = Game(gameNumber)
    for(turn in line.split(":")[1].split(";")) {
        val newTurn = Turn()
        for(draw in turn.split(",")) {
            val count = draw.trim().split(' ')[0].trim().toInt()
            val color = draw.trim().split(' ')[1].trim()
            newTurn.addCubes(color, count)
        }
        newGame.addTurn(newTurn)
    }

    return newGame
}

class Game (val gameNumber:Int) {
    private val turns: ArrayList<Turn> = ArrayList()

    fun addTurn(newTurn: Turn) {
        turns.add(newTurn)
    }

    fun getFewestRedCount(): Int {
        var maxRedCount = 0
        turns.stream().filter { it.cubes.containsKey("red") }.forEach {
            val redCount = it.cubes.getOrDefault("red", maxRedCount)
            if (redCount > maxRedCount) {
                maxRedCount = redCount
            }
        }
        return maxRedCount
    }

    fun getFewestGreenCount(): Int {
        var maxGreenCount = 0
        turns.stream().filter { it.cubes.containsKey("green") }.forEach {
            val redCount = it.cubes.getOrDefault("green", maxGreenCount)
            if (redCount > maxGreenCount) {
                maxGreenCount = redCount
            }
        }
        return maxGreenCount
    }

    fun getFewestBlueCount(): Int {
        var maxBlueCount = 0
        turns.stream().filter { it.cubes.containsKey("blue") }.forEach {
            val redCount = it.cubes.getOrDefault("blue", maxBlueCount)
            if (redCount > maxBlueCount) {
                maxBlueCount = redCount
            }
        }
        return maxBlueCount
    }

}

class Turn {
    var cubes:HashMap<String, Int> = HashMap()

    fun addCubes(color:String, count:Int) {
        cubes[color] = count
    }
}
