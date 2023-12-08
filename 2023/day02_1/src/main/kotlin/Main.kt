import java.io.File

fun main(args: Array<String>) {
    println("AOC 2023, Day 2, Part 1 starting!!!")

    val games:ArrayList<Game> = ArrayList()
    File(args[0]).forEachLine {
        games.add(parseGameLine(it))
    }

    var answer = 0
    games.stream()
        .filter { it.checkRedCount() }
        .filter { it.checkGreenCount() }
        .filter { it.checkBlueCount() }
        .forEach {
            print("Game #${it.gameNumber} Passed, ")
            answer += it.gameNumber }

    println("")
    println("Answer: $answer")

    println("AOC 2023, Day 2, Part 1 completed!!")
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
    private val turns:ArrayList<Turn> = ArrayList()

    fun addTurn(newTurn:Turn) {
        turns.add(newTurn)
    }

    fun checkRedCount():Boolean {
        for(turn in turns) {
            if((turn.cubes["red"] ?: 0) > 12) {
                return false
            }
        }
        return true
    }

    fun checkGreenCount():Boolean {
        for(turn in turns) {
            if((turn.cubes["green"] ?: 0) > 13) {
                return false
            }
        }
        return true
    }

    fun checkBlueCount():Boolean {
        for(turn in turns) {
            if((turn.cubes["blue"] ?: 0) > 14) {
                return false
            }
        }
        return true
    }
}

class Turn {
    var cubes:HashMap<String, Int> = HashMap()

    fun addCubes(color:String, count:Int) {
        cubes[color] = count
    }
}

