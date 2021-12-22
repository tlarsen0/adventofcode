import java.io.File
import java.util.*
import kotlin.collections.HashMap

fun main(args: Array<String>) {
    println("AOC 2021, Day 21.1 starting!!!!")


    val player1 = Day21Player()
    val player2 = Day21Player()
    val gameBoard = Board()
    // args[0] should read /Users/tlarsen/src/adventofcode/2021/DAY-PART/src/main/resources
    File(args[0]).forEachLine {
        val split = it.split(":")

        if(player1.isNotSet()) {
            player1.position = split[1].trim().toInt()
        } else if(player2.isNotSet()) {
            player2.position = split[1].trim().toInt()
        }
    }

    gameBoard.addPlayer("player1", player1)
    gameBoard.addPlayer("player2", player2)

    gameBoard.addPlayerOrder("player1")
    gameBoard.addPlayerOrder("player2")

    var turnCount = 1
    while(!gameBoard.hasWon()) {
        gameBoard.nextTurn()
        gameBoard.postScores(turnCount)
        turnCount++
    }

    val loserScore = Math.min(player1.score, player2.score)
    println("Answer To Day 20 question: " + (loserScore * gameBoard.dice.diceRollCount))

    println("AOC 2021, Day 21.1 complete!!!!")
}

class Board {
    var players = HashMap<String, Day21Player>()
    var playerOrder = LinkedList<String>()
    val dice = DeterministicDice()

    fun addPlayer(name: String, player: Day21Player) {
        players[name] = player
    }

    fun addPlayerOrder(playerName: String) {
        playerOrder.add(playerName)
    }

    fun nextTurn() {
        for(currentPlayerName in playerOrder) {
            val currentPlayer = players[currentPlayerName]

            for(rolls in 0 .. 2) {
                // roll the dice
                val roll = dice.nextRoll()
                // move currentPlayer forward dice positions
                currentPlayer!!.position += roll
                // If player is beyond 10 they must wrap around to 1. Simulate this by doing mod 10.
                while(currentPlayer!!.position > 10) {
                    currentPlayer.position -= 10
                }
            }

            // Record score of the player's position
            currentPlayer!!.score += currentPlayer!!.position
            if(currentPlayer.score >= 1000) {
                return
            }
        }
    }

    fun hasWon() : Boolean {
        for(p in players) {
            if (p.value.score >= 1000) {
                return true
            }
        }
        return false
    }

    fun postScores(turnCount:Int) {
        println("Turn $turnCount")
        for(p in players) {
            println("Player ${p.key}: ${p.value.score}")
        }
    }
}

class DeterministicDice {
    private var theNextRoll = 1
    var diceRollCount = 0

    fun nextRoll() : Int {
        val theRoll = theNextRoll
        theNextRoll++
        if(theNextRoll > 100) {
            theNextRoll = 1
        }
        diceRollCount++
        return theRoll
    }
}

class Day21Player {
    var position : Int = -1
    var score : Int = 0

    fun isNotSet() : Boolean {
        return position == -1
    }
}